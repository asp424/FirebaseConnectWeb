package dev.test.ru.ui.cells.chat

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.data.sources.sendMessage
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.TextInput

@Composable
fun inputBar(inputIsVisible: Boolean) {

    var textMessage by remember { mutableStateOf("") }

    Row(
        Modifier.margin(top = (screenHeight - animateDpAsState(
                if (inputIsVisible) 100.dp else ((-200).dp), tween(500)
        ).value.value).px
        ), Arrangement.Center, Alignment.CenterVertically
    ) {
        TextInput(textMessage) {

            style { width(500.px); height(30.px) }

            onInput { textMessage = it.value }
        }

        Image(
            "icon-512.png", Modifier.onClick {
                if (textMessage.trim().isNotEmpty()) {
                    sendMessage(textMessage); textMessage = ""
                }
            }.margin(left = 10.px), width = 30, height = 30
        )
    }
}
