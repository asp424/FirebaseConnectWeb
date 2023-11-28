package dev.test.ru.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.Red
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.ui.states.AuthType
import dev.test.ru.ui.states.UIStates.email
import dev.test.ru.ui.states.UIStates.filterEmail
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import dev.test.ru.ui.states.UIStates.password
import dev.test.ru.ui.states.UIStates.screenHeight
import dev.test.ru.ui.states.UIStates.screenWidth
import dev.test.ru.ui.states.UIStates.startAuth
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun RegScreen() {

    val error = remember { mutableStateOf("") }

    val progress = remember { mutableStateOf(false) }

    val buttonsEn = remember { mutableStateOf(true) }

    val progressScale = animateFloatAsState(if (progress.value) 1f else 0f).value

    val textErrorScaleBool = remember { mutableStateOf(false) }

    val textErrorScale = animateFloatAsState(
        if (textErrorScaleBool.value) 5f else 0.8f, tween(1000),
        finishedListener = {
            if (it == 5f) textErrorScaleBool.value = false
        }
    ).value

    LaunchedEffect(Unit) {
        buttonsEn.value = true
        mainProgressIsVisible.value = false
        textErrorScaleBool.value = false
    }

    Column(
        Modifier.padding(bottom = 30.px)
            .margin(left = ((screenWidth / 2) - 100).px, top = ((screenHeight / 2) - 280).px),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Image("icon-512.png", Modifier.margin(bottom = 20.px), width = 200, height = 200)

        TextInput(email.value) {
            placeholder("Email")
            style { width(200.px); height(30.px); marginBottom(10.px); marginTop(10.px) }
            onInput { s -> filterEmail(s) }
        }

        TextInput(password.value) {
            placeholder("Password")
            style { width(200.px); height(30.px); marginTop(10.px) }
            onInput { password.value = it.value }
        }

        Box(Modifier.scale(textErrorScale), Alignment.BottomCenter) {
            Text("Минимум 6 символов")
        }

        Box(Modifier.padding(bottom = 10.px, top = 20.px), Alignment.BottomCenter) {
            Button({
                style { width(150.px) }
                onClick { startAuth(AuthType.SIGNIN, buttonsEn, progress, error, textErrorScaleBool) }
            }) { Text("Вход") }
        }

        Button({
            style { width(150.px) }
            onClick { startAuth(AuthType.SIGNUP, buttonsEn, progress, error, textErrorScaleBool) }
        }) { Text("Регистрация") }

        Box(Modifier.scale(progressScale).margin(top = 80.px), Alignment.BottomCenter) {
            Progress({ style { color(CadetBlue) } })
        }
    }

    Box(
        Modifier.fillMaxWidth().scale(1 - progressScale)
            .margin(top = (screenHeight - 160).px), Alignment.Center
    ) {
        P({ style { color(Red) } }) { Text(error.value) }
    }
}
