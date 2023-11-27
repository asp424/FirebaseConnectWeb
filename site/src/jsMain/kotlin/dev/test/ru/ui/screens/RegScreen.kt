package dev.test.ru.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.data.models.Nodes
import dev.test.ru.data.sources.authWithEmail
import dev.test.ru.data.sources.save
import dev.test.ru.data.sources.signInWithEmail
import dev.test.ru.ui.states.UIStates.email
import dev.test.ru.ui.states.UIStates.myDigit
import dev.test.ru.ui.states.UIStates.password
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun RegScreen() {

    var errorText by remember {
        mutableStateOf("")
    }
    var progressIsVisible by remember {
        mutableStateOf(false)
    }
    var buttonsEnable by remember {
        mutableStateOf(true)
    }
    var job: Job = remember {
        Job().apply { cancel() }
    }

    val buttonsHeight = animateDpAsState(
        if (progressIsVisible) 1.dp else 0.dp
    ).value.value
    LaunchedEffect(true) {
        buttonsEnable = true
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 30.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            "icon-512.png",
            width = 200, height = 200, modifier = Modifier.margin(bottom = 20.px)
        )

        TextInput(email.value) {
            placeholder("Email")
            style {
                width(200.px)
                height(30.px)
                marginBottom(10.px)
                marginTop(10.px)
            }
            onInput { email.value = it.value }
        }
        TextInput(password.value) {
            placeholder("Password")
            style {
                width(200.px)
                height(30.px)
                marginTop(10.px)
            }
            onInput { password.value = it.value }
        }

        Box(Modifier.padding(bottom = 80.px), contentAlignment = Alignment.BottomCenter) {
            P {
                Button({
                    style {
                        marginBottom(20.px)
                        width(150.px)
                    }
                    onClick {
                        if (email.value.contains("@")
                            && email.value.contains(".")
                            && email.value.isNotEmpty()
                            && password.value.isNotEmpty()
                            && buttonsEnable
                        ) {
                            buttonsEnable = false
                            job.cancel()
                            job = CoroutineScope(Default).launch {
                                progressIsVisible = true
                                signInWithEmail(email.value, password.value) {
                                    myDigit = it
                                    save(email.value, Nodes.NAME, path = it)
                                }
                            }
                            CoroutineScope(Default).launch {
                                delay(15000)
                                progressIsVisible = false
                                errorText =
                                    "Неверные почта или пароль или отсутствует интернет"
                                delay(3000)
                                errorText = ""
                                job.cancel()
                                buttonsEnable = true
                                cancel()
                            }
                        }
                    }
                }) {
                    Text("Вход")
                }
            }
            Button({
                style {
                    width(150.px)
                }
                onClick {
                    if (email.value.contains("@")
                        && email.value.contains(".")
                        && email.value.isNotEmpty()
                        && password.value.isNotEmpty()
                        && buttonsEnable
                    ) {
                        buttonsEnable = false
                        job.cancel()
                        job = CoroutineScope(Default).launch {
                            progressIsVisible = true
                            authWithEmail(email.value, password.value) {
                                myDigit = it
                                save(email.value, Nodes.NAME, path = it)
                            }
                        }
                        CoroutineScope(Default).launch {
                            delay(15000)
                            progressIsVisible = false
                            errorText =
                                "Такая почта уже есть в базе или отсутствует интернет"
                            delay(3000)
                            errorText = ""
                            job.cancel()
                            buttonsEnable = true
                            cancel()
                        }
                    }
                }
            }) {
                Text("Регистрация")
            }
        }

        Box(
            Modifier.padding(top = 20.px).scale(buttonsHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            Progress {

            }
        }
        Box(
            Modifier.fillMaxWidth().padding(bottom = 50.px),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(errorText)
        }
    }
}
