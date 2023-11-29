package dev.test.ru.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.test.ru.data.models.MessageModel
import dev.test.ru.data.models.Nodes
import dev.test.ru.data.models.UserModel
import dev.test.ru.data.sources.authWithEmail
import dev.test.ru.data.sources.save
import dev.test.ru.data.sources.signInWithEmail
import dev.test.ru.ui.states.UIStates.set
import kotlinx.browser.window
import kotlinx.coroutines.*
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.w3c.dom.HTMLInputElement

object UIStates {
    var mainListUsers = mutableStateOf(listOf<UserModel>())
    var chatMessages = mutableStateOf(listOf<MessageModel>())
    val isShowAbleInfoBox get() = chatIndex.value >= 0 && mainListUsers.value.isNotEmpty()
    var myIdText = mutableStateOf("ass")
    var chatIndex = mutableStateOf(-1)
    var mainScreenIsVisible = mutableStateOf("0")
    var mainProgressIsVisible = mutableStateOf(false)
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var myDigit = ""
    var chatUserDigit = ""
    var screenWidth = window.innerWidth
    var screenHeight = window.innerHeight
    private var regJob: Job = Job().apply { cancel() }

    private fun checkForStartReg(
        buttonsEnable: MutableState<Boolean>,
        textErrorScaleBool: MutableState<Boolean>,
        onSuccess: () -> Unit
    ) {
        if (
            email.value.contains("@")
            && email.value.contains(".")
            && email.value.isNotEmpty()
            && password.value.length >= 6
            && buttonsEnable.value
        ) onSuccess()
        else if (password.value.length < 6) textErrorScaleBool.value = true
    }

    fun startAuth(
        authType: AuthType,
        buttonsEnable: MutableState<Boolean>,
        progressIsVisible: MutableState<Boolean>,
        textError: MutableState<String>,
        textErrorScaleBool: MutableState<Boolean>,
    ) {
        checkForStartReg(buttonsEnable, textErrorScaleBool) {
            buttonsEnable.value = false
            regJob.cancel()
            regJob = CoroutineScope(Dispatchers.Default).launch {
                progressIsVisible.value = true
                if (authType == AuthType.SIGNIN)
                    signInWithEmail(email.value, password.value) { myDigit = it }
                else authWithEmail(email.value, password.value) {
                    myDigit = it
                    save(email.value, Nodes.NAME, path = it)
                }
            }
            cancelRegJob(textError, authType, progressIsVisible, buttonsEnable)
        }
    }

    private fun cancelRegJob(
        textError: MutableState<String>,
        authType: AuthType,
        progressIsVisible: MutableState<Boolean>,
        buttonsEnable: MutableState<Boolean>
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(15000)
            textError.value = if (authType == AuthType.SIGNIN)
                "Неверные почта или пароль или отсутствует интернет"
            else "Такая почта уже есть в базе или отсутствует интернет"
            progressIsVisible.value = false
            delay(3000)
            textError.value = ""
            buttonsEnable.value = true
            regJob.cancel()
            cancel()
        }
    }


    fun filterEmail(s: SyntheticInputEvent<String, HTMLInputElement>) {
        s.data?.apply {

            if (single() == '@') {
                if (!s.value.aroundChar(this).contains('@')) s.set
            }

            else email.value = s.value.filter {
                if (it != '@') it.isLetterOrDigit() else true
            }

        }?:"".apply { s.set }
    }

    private val SyntheticInputEvent<String, HTMLInputElement>.set get() = run { email.value = value }
    private fun String.aroundChar(char: String) = "${substringBefore(char)}${substringAfter(char)}"
}

