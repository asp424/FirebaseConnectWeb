package dev.test.ru.data

import androidx.compose.ui.unit.dp
import com.ionspin.kotlin.crypto.secretbox.SecretBox
import com.ionspin.kotlin.crypto.util.LibsodiumRandom
import com.ionspin.kotlin.crypto.util.decodeFromUByteArray
import com.ionspin.kotlin.crypto.util.encodeToUByteArray
import com.lm.firebaseconnect.models.TypeMessage
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.graphics.Colors.Gray
import com.varabyte.kobweb.compose.ui.graphics.Colors.Green
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.test.ru.data.models.ImageModel
import dev.test.ru.data.models.LoadImageStates
import dev.test.ru.data.models.Locations
import dev.test.ru.data.models.MessageModel
import dev.test.ru.data.models.Nodes
import dev.test.ru.models.UserModel
import dev.test.ru.states.UIStates.encodedText
import dev.test.ru.states.UIStates.mainListUsers
import dev.test.ru.states.UIStates.myDigit
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.digest.SHA512
import dev.whyoleg.cryptography.algorithms.symmetric.AES
import dev.whyoleg.cryptography.algorithms.symmetric.SymmetricKeySize
import dev.whyoleg.cryptography.providers.webcrypto.WebCrypto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val databaseReference by lazy { Firebase.database(initialize()).reference() }

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

val fBAuth by lazy { Firebase.auth(initialize()) }

fun setAuthStateListener(status: (String) -> Unit) {

    statusJob.cancel()
    statusJob = CoroutineScope(Default).launch {
        fBAuth.authStateChanged.collect {
            status(
                when (it) {
                    null -> "1"
                    else -> "2"
                }
            )
        }
    }
}

fun signOut() {
    CoroutineScope(Default).launch {
        fBAuth.signOut()
    }
}

val getUid by lazy {
    fBAuth.currentUser?.uid?.apply {
        checkId?.also { myId -> myDigit = myId }
    }
}

fun String.getPairPath(myDigit: String) =
    if (checkDigits(myDigit, this)) "${F_U_S}${
        maxOf(myDigit.removeZero, removeZero)
    }${F_U_E}${S_U_S}${minOf(myDigit.removeZero, removeZero)}${S_U_E}"
    else "000000"

fun getPairPathFromRemoteMessage(myDigit: String, chatId: String) =
    if (checkDigits(myDigit, chatId)) "${F_U_S}${
        maxOf(myDigit.removeZero, chatId.removeZero)
    }${F_U_E}${S_U_S}${
        minOf(myDigit.removeZero, chatId.removeZero)
    }${S_U_E}" else "111111"

private fun checkDigits(myDigit: String, chatId: String) =
    myDigit.any { it.isDigit() } && chatId.any { it.isDigit() }

private val String.removeZero
    get() = takeIf { it.isNotEmpty() }?.filter { it != '0' }?.toInt() ?: 1984

private val String.checkId
    get() = if (isNotEmpty()) filter { v -> v.isDigit() && v != '0' } else null

var mainListJob: Job = Job().apply { cancel() }

var statusJob: Job = Job().apply { cancel() }

fun startMainListListener() {
    mainListJob.cancel()
    mainListJob = CoroutineScope(Default).launch {
        databaseReference.valueEvents.collect {
            mainListUsers.value = it.children.map { u -> u.getUserModel("") }
        }
    }
}

private fun startMessagesListener(userDigit: String, onDone: List<MessageModel>.() -> Unit) =
    CoroutineScope(Default).launch {
        databaseReference.child(Nodes.CHATS.node())
            .child(userDigit.getPairPath(myDigit)).valueEvents.collect {
                var prevDate = ""
                onDone(it.children.map { v ->
                    val message = v.value.toString()
                    val key = v.key.toString()
                    message.getMessageModel(key, prevDate).apply {
                        //  prevDate = with(firebaseSave.timeConverter) {
                        //     formatDate(message.parseTimestamp())
                        // }
                    }
                })
            }
    }

private fun String.encrypt(encrypted: (String) -> Unit) {
    encodeText(this) {
        encrypted(it.ifEmpty { "Сообщений пока нет" })
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun encodeText(text: String = "ass", encoded: (String) -> Unit) {
    CoroutineScope(Default).launch {
        val message = ("Ladies and Gentlemen of the class of '99: If I could offer you " +
                "only one tip for the future, sunscreen would be it.").encodeToUByteArray()
        val key = LibsodiumRandom.buf(32)

        val nonce = LibsodiumRandom.buf(24)

        val encrypted = SecretBox.easy(message, nonce, key)
        val decrypted = SecretBox.openEasy(encrypted, nonce, key)
        encodedText.value = encrypted.contentToString()
        delay(5000)
        encodedText.value = decrypted.decodeFromUByteArray()
    }
}

fun ass() {
    CoroutineScope(Default).launch {
        encodedText.value = "text1".encodeToByteArray().decodeToString()
        delay(4000)
        val aesCbc = CryptographyProvider.WebCrypto
            .get(AES.CBC)
        val keyGenerator = aesCbc.keyGenerator(SymmetricKeySize.B256)

        val key: AES.CBC.Key = keyGenerator.generateKey()
        val cipher = key.cipher()
        val ciphertext: ByteArray = cipher.encrypt("text1".encodeToByteArray())
        encodedText.value = ciphertext.joinToString()

        delay(4000)

        encodedText.value = cipher.decrypt(ciphertext).decodeToString()
        delay(4000)
        val encodedKey = key.encodeTo(AES.Key.Format.RAW).decodeToString()
        encodedText.value = encodedKey
        val decodedKey: AES.CBC.Key = aesCbc.keyDecoder().decodeFrom(AES.Key.Format.RAW, encodedKey.encodeToByteArray())
        val decodedKeyCipher = decodedKey.cipher()
        println(decodedKeyCipher.decrypt(ciphertext).decodeToString())
    }
}

fun decodeText(text: String, decoded: (String) -> Unit) {
    CoroutineScope(Default).launch {

    }
}

private fun String.getMessageModel(key: String, prevDate: String) =
    with("ass") {
        val digit = parseDigit()
        val side = if (digit == myDigit) MY_COLOR
        else CHAT_ID_COLOR
        val wasRead = if (side == MY_COLOR && !startsWith(NEW)) 1.dp else 0.dp
        //val date = formatDate(parseTimestamp())
        val type = when {
            contains(IS_RECORD) -> TypeMessage.VOICE
            contains(IS_IMAGE) -> TypeMessage.IMAGE
            else -> TypeMessage.MESSAGE
        }
        val isNew = startsWith(NEW)
        val replyKey = getReplyKey()
        val isReply = replyKey.isNotEmpty()
        MessageModel(
            type = type,
            text = getText(),
            alignment = if (side == MY_COLOR) Alignment.CenterEnd else Alignment.CenterStart,
            key = key,
            //time = getTimeToMessage(),
            timeStamp = parseTimestamp(),
            name = getName(),
            wasRead = wasRead,
            wasReadColor = if (side == MY_COLOR && wasRead == 1.dp) Green else Gray,
            digit = digit,
            mustSetWasRead = side != MY_COLOR && isNew,
            // date = formatDate(parseTimestamp()),
            // isNewDate = prevDate != date,
            topStartShape = if (side == MY_COLOR) 20.dp else 0.dp,
            bottomEndShape = if (side == MY_COLOR) 0.dp else 20.dp,
            isReply = isReply,
            replyKey = replyKey,
            isNew = side == MY_COLOR && isNew
        ).apply {
            if (type == TypeMessage.IMAGE)
                imageMessage = ImageModel(
                    getLocalImageUri(), parseLoadState(), key
                ).apply {
                    if (uploadState != LoadImageStates.NULL && alignment == Alignment.CenterStart)
                        isShow = false
                }
            if (type == TypeMessage.VOICE)
                voiceTimeStamp = getVoiceTimestamp()
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

private fun String.getName() = substringAfter(D_T_E).substringBefore(T_T_S)

private fun String.parseDigit() = substringAfter(D_T_S).substringBefore(D_T_E)

private fun String.removeKey() = substringAfter("):")

private fun String.getReplyKey() = substringAfter(R_T_S).substringBefore(R_T_E)

private fun String.getText() = substringAfter(T_T_E).substringBefore(R_T_S)

private fun String.getVoiceTimestamp() = substringAfter(IS_RECORD).substringBefore(R_T_S)

private fun String.getLocalImageUri() = substringAfter(IS_IMAGE).substringBefore(T_I_S)

fun String.parseTimestamp() = substringAfter(T_T_S).substringBefore(T_T_E)

fun DataSnapshot.getUserModel(pairPath: String) = UserModel(
    id = key ?: "",
    name = getValue(key ?: "", Nodes.NAME),
    onLine = getValue(key ?: "", Nodes.ONLINE),
    //isWriting = getValue(pairPath, Nodes.WRITING),
    token = getValue(key ?: "", Nodes.TOKEN),
    //lastMessage = getValue(pairPath, Nodes.LAST).decrypt().removeKey().ifEmpty { EMPTY },
    iconUri = getValue(key ?: "", Nodes.ICON),
    isFree = getValue("callState", Nodes.CALL) != ANSWER,
    locations = Locations(
        getValue("latitude", Nodes.LOC),
        getValue("longitude", Nodes.LOC),
        getValue("gps_time", Nodes.LOC)
    )
)

fun DataSnapshot.getValue(path: String, node: Nodes) =
    child(node.node()).child(path).value?.run { toString() } ?: ""

suspend fun authWithEmail(email: String, password: String) {
    fBAuth.createUserWithEmailAndPassword(email, password)
}

suspend fun signInWithEmail(email: String, password: String) {
    fBAuth.signInWithEmailAndPassword(email, password)
}

const val F_U_S = "<f>"
const val F_U_E = "<*f>"
const val S_U_S = "<s>"
const val S_U_E = "<*s>"
const val D_T_S = "<N>"
const val D_T_E = "</N>"
const val MY_COLOR = "green"
const val CHAT_ID_COLOR = "black"
const val IS_RECORD = "<*R>isRecord<*/R>"
const val IS_IMAGE = "<*I>isImage<*/I>"
const val I_L_S = "<*IL>"
const val I_L_E = "<*/IL>"
const val USER_DELIMITER_START = "<*US>"
const val USER_DELIMITER_END = "<*/US>"
const val T_I_S = "<*TI>"
const val T_I_E = "<*/TI>"
const val NEW = "<*New>"
const val R_T_S = "<*RP>"
const val R_T_E = "<*/RP>"
const val EMPTY = "empty"
const val ERROR = "error"
const val TOKEN = "registration_ids"
const val DATA = "data"
const val NAME = "name"
const val ICON = "icon"
const val TITLE = "title"
const val KEY = "keyMessage"
const val PAIR_PATH = "pairPath"
const val TYPE_MESSAGE = "typeMessage"
const val INCOMING_CALL = "incomingCall"
const val OUTGOING_CALL = "outgoingCall"
const val CALL = "call"
const val CALL_STATE = "callState"
const val CALL_TYPE = "callType"
const val ANSWER = "answer"
const val REJECT = "reject"
const val RESET = "reset"
const val MESSAGE = "message"
const val WAIT = "wait"
const val BUSY = "busy"
const val GET_INCOMING_CALL = "getIncomingCall"
const val INCOMING_CALL_FROM_NOTIFY = "incomingCallFromNotify"
const val CALLING_ID = "callingId"
const val DESTINATION_ID = "destinationId"
const val GET_CHECK_FOR_CALL = "getCheckForCall"
const val CHECK_FOR_CALL = "checkForCall"
const val NOTIFY_CALLBACK = "notifyCallback"
const val REJECTED_CALL = "rejectedCall"
const val VOICE = "voice"
const val VIDEO = "video"
const val T_T_S = "<T>"
const val T_T_E = "</T>"



