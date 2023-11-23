package dev.test.ru.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.test.ru.data.models.Locations
import dev.test.ru.data.models.Nodes
import dev.test.ru.models.UserModel
import dev.test.ru.states.UIStates.firebaseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
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
    mainListJob.cancel()
    mainListJob = CoroutineScope(Default).launch {
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

var mainListJob: Job = Job().apply { cancel() }

fun startMainListListener() {
    mainListJob.cancel()
    mainListJob = CoroutineScope(Default).launch {
        databaseReference.valueEvents.collect {
            firebaseData.value = it.children.map { u -> u.getUserModel("") }
        }
    }
}

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



