package dev.test.ru.ui.cells

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.data.sources.sendMessage
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.TextInput

@Composable
fun InputBar() {
    var textMessage by remember {
        mutableStateOf("")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
        modifier = Modifier.margin(bottom = 30.px, top = 5.px)
    ) {
        TextInput(textMessage) {
            style {
                width(500.px)
                height(30.px)
            }
            onInput { textMessage = it.value }
        }
        Image(
            "icon-512.png",
            width = 30, height = 30, modifier = Modifier.onClick {
                if (textMessage.trim().isNotEmpty()) {
                    sendMessage(textMessage)
                    textMessage = ""
                }
            }.margin(left = 10.px)
        )
    }
}