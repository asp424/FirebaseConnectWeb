package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.*
import dev.test.div


@Composable
fun chatBox(isExpandedMain: Boolean, onInvisibleChatBox: () -> Unit) {

    var y by remember { mutableStateOf(0.0) }

    var inputIsVisible by remember { mutableStateOf(false) }

    chatWindow(isExpandedMain, onInvisibleChatBox, {
        inputIsVisible = true
    }) {

        div("overflow-wrap", "break-word", onWheel = {
            if (y >= 0) {
                y -= it.deltaY / 2; if (y < 0) y = 0.0
            }
        }) { listMessagesChat(y) }
        inputBar(inputIsVisible)
    }
}

