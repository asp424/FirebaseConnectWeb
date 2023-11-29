package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.width
import dev.test.ru.ui.states.UIStates.chatIndex
import dev.test.ru.ui.states.UIStates.mainListUsers
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun chatIdText() {
    P({ style { paddingBottom(15.px); paddingLeft(90.px) } }) {

        B({ style { color(White) } }) {

            Box(modifier = Modifier.width(200.px)) {

                Text(
                    mainListUsers.value[chatIndex.value].name.ifEmpty {
                        mainListUsers.value[chatIndex.value].id
                    }
                )
            }
        }
    }
}