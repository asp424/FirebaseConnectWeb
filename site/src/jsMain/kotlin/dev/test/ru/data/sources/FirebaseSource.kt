package dev.test.ru.data.sources

import androidx.compose.ui.unit.dp
import com.lm.firebaseconnect.models.TypeMessage
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.graphics.Colors.Gray
import com.varabyte.kobweb.compose.ui.graphics.Colors.Green
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.database.rethrow
import dev.gitlive.firebase.initialize
import dev.test.ru.core.Crypto
import dev.test.ru.data.*
import dev.test.ru.data.models.*
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.chatMessages
import dev.test.ru.ui.states.UIStates.chatUserDigit
import dev.test.ru.ui.states.UIStates.email
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import dev.test.ru.ui.states.UIStates.myDigit
import dev.test.ru.ui.states.UIStates.myIdText
import dev.test.ru.ui.states.UIStates.password
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.utils.io.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

private val databaseReference by lazy { Firebase.database(initialize()).reference() }

val crypto by lazy { Crypto() }

val firebaseParser by lazy { FirebaseParser() }

val fBAuth by lazy { Firebase.auth(initialize()) }

var mainListJob: Job = Job().apply { cancel() }

var chatJob: Job = Job().apply { cancel() }

var statusJob: Job = Job().apply { cancel() }

private val client by lazy { HttpClient() }

fun signOut() {
    CoroutineScope(Default).launch { fBAuth.signOut() }
}

fun getNewKey() = databaseReference.push().key.toString()

fun initialize() =
    Firebase.initialize(
        options = FirebaseOptions(
            "1:700634303386:web:f83039127b4258a3230823",
            "AIzaSyDz5DCugxU4N6pkxzAH-LL95QWcglUFyp0",
            "https://fir-connect-a05e5-default-rtdb.firebaseio.com",
            "fir-connect-a05e5",
            "fir-connect-a05e5.appspot.com",
            "fir-connect-a05e5",
            "700634303386",
            "fir-connect-a05e5.firebaseapp.com"
        )
    )

fun setAuthStateListener(status: (String) -> Unit) {
    statusJob.cancel()
    statusJob = CoroutineScope(Default).launch {
        fBAuth.authStateChanged.collect {
            status(
                when (it) {
                    null -> "1"
                    else -> "2".apply {
                        it.uid.apply {
                            checkId?.also { myId ->
                                myDigit = myId
                                myIdText.value = "ID: $myId"
                                mainProgressIsVisible.value = true
                                email.value = ""
                                password.value = ""
                            }
                        }
                    }
                }
            )
        }
    }
}

fun String.getPairPath(myDigit: String) =
    if (checkDigits(myDigit, this)) "$F_U_S${
        maxOf(myDigit.removeZero, removeZero)
    }$F_U_E$S_U_S${minOf(myDigit.removeZero, removeZero)}$S_U_E"
    else "000000"

fun getPairPathFromRemoteMessage(myDigit: String, chatId: String) =
    if (checkDigits(myDigit, chatId)) "$F_U_S${
        maxOf(myDigit.removeZero, chatId.removeZero)
    }$F_U_E$S_U_S${
        minOf(myDigit.removeZero, chatId.removeZero)
    }$S_U_E" else "111111"

private fun checkDigits(myDigit: String, chatId: String) =
    myDigit.any { it.isDigit() } && chatId.any { it.isDigit() }

private val String.removeZero
    get() = takeIf { it.isNotEmpty() }?.filter { it != '0' }?.toInt() ?: 1984

private val String.checkId
    get() = if (isNotEmpty()) filter { v -> v.isDigit() && v != '0' } else null


fun startMainListListener() {
    mainListJob.cancel()
    mainListJob = CoroutineScope(Default).launch {
        databaseReference.valueEvents.collect {
            mainListUsers.value = it.children.map { u -> u.getUserModel("") }
        }
    }
}

fun startMessagesListener(onDone: List<MessageModel>.() -> Unit) {
    chatJob.cancel()
    chatJob = CoroutineScope(Default).launch {
        databaseReference.child(Nodes.CHATS.node())
            .child(chatUserDigit.getPairPath(myDigit)).valueEvents.collect {
                var prevDate = ""
                onDone(it.children.map { v ->
                    with(crypto) {
                        val message = v.value.toString().decrypting()
                        val key = v.key.toString()
                        message.getMessageModel(key, prevDate).apply {
                            //  prevDate = with(firebaseSave.timeConverter) {
                            //     formatDate(message.parseTimestamp())
                            // }
                        }
                    }
                }
                )
            }
    }
}

private fun String.getMessageModel(key: String, prevDate: String) =
    with(0) {
        val digit = with(firebaseParser) { parseDigit() }
        val side = if (digit == myDigit) MY_COLOR
        else CHAT_ID_COLOR
        val wasRead = if (side == MY_COLOR && !startsWith(NEW)) 1.dp else 0.dp
        val date = with(firebaseParser) { parseTimestamp() }
        val type = when {
            contains(IS_RECORD) -> TypeMessage.VOICE
            contains(IS_IMAGE) -> TypeMessage.IMAGE
            else -> TypeMessage.MESSAGE
        }
        val isNew = startsWith(NEW)
        val replyKey = with(firebaseParser) { getReplyKey() }
        val isReply = replyKey.isNotEmpty()
        MessageModel(
            type = type,
            text = with(firebaseParser) { getText() },
            alignment = if (side == MY_COLOR) Alignment.CenterEnd else Alignment.CenterStart,
            key = key,
            //time = getTimeToMessage(),
            timeStamp = with(firebaseParser) { parseTimestamp() }.apply {

            },
            name = with(firebaseParser) { getName() },
            wasRead = wasRead,
            wasReadColor = if (side == MY_COLOR && wasRead == 1.dp) Green else Gray,
            digit = digit,
            mustSetWasRead = side != MY_COLOR && isNew,
            // date = formatTimestamp(parseTimestamp()),
            // isNewDate = prevDate != date,
            topStartShape = if (side == MY_COLOR) 20.dp else 0.dp,
            bottomEndShape = if (side == MY_COLOR) 0.dp else 20.dp,
            isReply = isReply,
            replyKey = replyKey,
            isNew = side == MY_COLOR && isNew
        ).apply {
            if (type == TypeMessage.IMAGE)
                imageMessage = ImageModel(
                    with(firebaseParser) { getLocalImageUri() }, parseLoadState(), key
                ).apply {
                    if (uploadState != LoadImageStates.NULL && alignment == Alignment.CenterStart)
                        isShow = false
                }
            if (type == TypeMessage.VOICE)
                voiceTimeStamp = with(firebaseParser) { getVoiceTimestamp() }
        }
    }

fun String.parseLoadState() =
    when (substringAfter(I_L_S).substringBefore(I_L_E)) {
        "startUpload" -> LoadImageStates.START_UPLOAD
        "uploaded" -> LoadImageStates.UPLOADED
        "loading" -> LoadImageStates.LOADING
        "encrypted" -> LoadImageStates.ENCRYPTED
        "null" -> LoadImageStates.NULL
        else -> LoadImageStates.NULL
    }

fun deleteAllMessages() {
    CoroutineScope(Default).launch {
        chatMessages.value.forEach { _ ->
            // if (it.type == TypeMessage.IMAGE || it.type == TypeMessage.VOICE)
            //    firebaseStorage.delete(it.key, context)
            // }
            child(myDigit, chatUserDigit).removeValue()
            with(
                crypto.encrypt("Сообщений пока нет")
            ) {
                save(this, Nodes.LAST)
                save(this, Nodes.LAST, child = chatUserDigit)
            }
        }
    }
}

fun child(myDigit: String, chatId: String) = databaseReference.child(Nodes.CHATS.node())
    .child(chatId.getPairPath(myDigit))

fun sendMessage(
    text: String,
    newFlag: String = NEW,
    timeStamp: String = "timestamp",
    key: String = getNewKey(),
    name: String = "myName",
    digit: String = myDigit,
    chatId: String = chatUserDigit,
    replyKey: String = "",
    isSendRemote: Boolean = true,
) = with(
    crypto.encrypt(
        "$newFlag${D_T_S}${digit}${D_T_E}${name}${T_T_S}$timeStamp${
            T_T_E
        }$text$R_T_S$replyKey$R_T_E"
    )
) {
    CoroutineScope(Default).launch {
        child(digit, chatId).updateChildren(mapOf(key to this@with))
        save(
            this@with, Nodes.LAST, myDigit = digit,
            chatId = chatId,
            child = digit
        ) {
            save(
                this@with, Nodes.LAST, myDigit = digit,
                chatId = chatId,
                child = chatId
            ) {
                //if (newFlag.isNotEmpty() && isSendRemote)
                // firebaseConnect.remoteMessages.message(text, key); onSend()
            }
        }
    }
}

//val currentTimeStamp get() = Calendar.getInstance().time.time.toString()

fun DataSnapshot.getUserModel(pairPath: String) = UserModel(
    id = key ?: "",
    name = getValue(key ?: "", Nodes.NAME),
    onLine = getValue(key ?: "", Nodes.ONLINE),
    //isWriting = getValue(pairPath, Nodes.WRITING),
    token = getValue(key ?: "", Nodes.TOKEN),
    // lastMessage = with(crypto){ getValue(pairPath, Nodes.LAST)
    //     .decrypting().removeKey().ifEmpty { EMPTY } },
    iconUri = getValue(key ?: "", Nodes.ICON),
    isFree = getValue("callState", Nodes.CALL) != ANSWER,
    locations = Locations(
        getValue("latitude", Nodes.LOC),
        getValue("longitude", Nodes.LOC),
        getValue("gps_time", Nodes.LOC)
    )
)

fun a() {
    console.log(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
}

fun DataSnapshot.getValue(path: String, node: Nodes) =
    child(node.node()).child(path).value?.run { toString() } ?: ""

suspend fun authWithEmail(email: String, password: String, onGood: (String) -> Unit) {
    fBAuth.createUserWithEmailAndPassword(email, password).rethrow {
        user?.uid?.checkId?.apply { onGood(this) }
    }
}

suspend fun signInWithEmail(email: String, password: String, onGood: (String) -> Unit) {
    fBAuth.signInWithEmailAndPassword(email, password).rethrow {
        user?.uid?.checkId?.apply { onGood(this) }
    }
}

fun save(
    value: String,
    node: Nodes,
    myDigit: String = UIStates.myDigit,
    chatId: String = chatUserDigit,
    child: String = myDigit,
    path: String = chatId.getPairPath(myDigit),
    onSave: () -> Unit = {}
) {
    CoroutineScope(Default).launch {
        databaseReference.child(child).child(node.node()).updateChildren(mapOf(path to value))
    }
}

@OptIn(InternalAPI::class)
suspend fun ass() {
    val response = client.post{
        url("https://fcm.googleapis.com/fcm/")
        headers {
            append(
                Authorization,
                "key=AAAAoyEPC5o:APA91bFan2FPNVGsLjebfDbm51TUz0-KPhcl86TZe9CwyYoOmTr631B5Axd7eRJ3qfg5PUC4SAKCJkndfmPCf2rq7fl9X1xzkFsitgiqQbQq4gtRHAc3keGyKoIs1O4TzNPSdgBT5HbK"
            )
            append(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        body = buildJsonObject {
            put(
                "to",
                "esWFmMQNREuXuF1-JrQ0Ul:APA91bHoddkj6PVdqdXjA68uod6zbe53tUIuuJG0Rd_jVeGXh8QLQzybglYlog1mbF9RmHGvIdSz3PRVQTvDt9GMKwXiMhnP2FWqCT98LtE0PpL1sAzO1-l6c671GHgOdOY6LbopkOUB"
            )
            putJsonObject(DATA) { put(TYPE_MESSAGE, "ass"); put(TOKEN, "ass"); put(MESSAGE, "Hello") }
        }.toString()
    }
    client.close()
    console.log(response)
}




