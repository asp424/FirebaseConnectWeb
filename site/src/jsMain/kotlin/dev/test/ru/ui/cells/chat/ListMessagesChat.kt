package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import dev.test.ru.ui.states.UIStates
import org.jetbrains.compose.web.css.px

@Composable
fun listMessagesChat(y: Double) =
    Column(
        Modifier.width(750.px).height(505.px).margin(top = y.px, bottom = 60.px),
        Arrangement.Bottom, Alignment.CenterHorizontally
    ) { UIStates.chatMessages.value.forEach { m -> textMessage(m) } }

