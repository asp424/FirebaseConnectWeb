package dev.test.ru.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.data.authWithEmail
import dev.test.ru.data.signInWithEmail
import dev.test.ru.states.UIStates.getStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Progress
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput

@Composable
fun RegScreen() {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var errorText by remember {
        mutableStateOf("")
    }
    var progressIsVisible by remember {
        mutableStateOf(false)
    }

    var job: Job = remember {
        Job().apply { cancel() }
    }

    val buttonsHeight = animateDpAsState(
        if (progressIsVisible) 1.dp else 0.dp
    ).value.value

    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 30.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            "icon-512.png",
            width = 200, height = 200, modifier = Modifier.margin(bottom = 20.px)
        )

        TextInput(email) {
            placeholder("Email")
            style {
                width(200.px)
                height(30.px)
                marginBottom(10.px)
                marginTop(10.px)
            }
            onInput { email = it.value }
        }
        TextInput(password) {
            placeholder("Password")
            style {
                width(200.px)
                height(30.px)
                marginTop(10.px)
            }
            onInput { password = it.value }
        }

        Box(Modifier.padding(bottom = 80.px), contentAlignment = Alignment.BottomCenter) {
            P {
                Button({
                    style {
                        marginBottom(20.px)
                        width(150.px)
                    }
                    onClick {
                        if (email.contains("@")
                            && email.contains(".")
                            && email.isNotEmpty()
                            && password.isNotEmpty()
                        ) {
                            job.cancel()
                            job = CoroutineScope(Default).launch {
                                progressIsVisible = true
                                signInWithEmail(email, password)
                            }
                            CoroutineScope(Default).launch {
                                delay(15000)
                                progressIsVisible = false
                                errorText = "Неудачно, попробуйте позже"
                                delay(2000)
                                errorText = ""
                                job.cancel()
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
                    if (email.contains("@")
                        && email.contains(".")
                        && email.isNotEmpty()
                        && password.isNotEmpty()
                    ) {
                        progressIsVisible = true
                        job.cancel()
                        job = CoroutineScope(Default).launch {
                            progressIsVisible = true
                            authWithEmail(email, password)
                        }
                        CoroutineScope(Default).launch {
                            delay(15000)
                            progressIsVisible = false
                            errorText = "Неудачно, попробуйте позже"
                            delay(2000)
                            errorText = ""
                            job.cancel()
                            cancel()
                        }
                    }
                }
            }) {
                Text("Регистрация")
            }
        }
        Text(getStatus.value)
        Box(Modifier.padding(top = 20.px).scale(buttonsHeight), contentAlignment = Alignment.BottomCenter) {
            Text(errorText)
            //if (progressIsVisible)
        Progress {

            }
        }
    }
}
