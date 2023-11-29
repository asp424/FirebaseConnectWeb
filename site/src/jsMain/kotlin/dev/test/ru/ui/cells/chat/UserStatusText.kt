package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.chatIndex
import dev.test.ru.ui.states.UIStates.mainListUsers
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Small

@Composable
fun userStatusText() {
    P({ style { paddingTop(15.px); paddingLeft(90.px); color(Colors.White) } }) {

        Small { SpanText(if (mainListUsers.value[chatIndex.value].onLine != "0") "в сети" else "не в сети") }
    }
}