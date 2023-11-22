package dev.test.ru.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.test.ru.data.models.Locations
import dev.test.ru.data.models.Nodes
import dev.test.ru.models.UserModel
import dev.test.ru.states.UIStates.firebaseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private val databaseReference by lazy {
    Firebase.database(
        Firebase.initialize(
            options = FirebaseOptions(
                "1:700634303386:android:2c1fd690f6d6765f230823",
                "AIzaSyBtEPcoy53H9Ni3zrox_7S7swSzv3Jnzow",
                "https://fir-connect-a05e5-default-rtdb.firebaseio.com",
                projectId = "fir-connect-a05e5"

            )
        )
    ).reference()
}

var mainListJob: Job = Job().apply { cancel() }

fun startMainListListener() {
    mainListJob.cancel()
    mainListJob = CoroutineScope(Dispatchers.Default).launch {
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

fun checkForAuth() = Firebase.auth.currentUser?.uid != null

suspend fun authWithEmail(email: String, password: String) {
    Firebase.auth.createUserWithEmailAndPassword(email, password).user.apply {
        
    }
}

