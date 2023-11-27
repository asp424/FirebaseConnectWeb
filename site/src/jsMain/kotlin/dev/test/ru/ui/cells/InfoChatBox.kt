package dev.test.ru.ui.cells

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaDeleteLeft
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.test.ru.data.sources.deleteAllMessages
import dev.test.ru.ui.states.UIStates
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Small
import org.jetbrains.compose.web.dom.Text

@Composable
fun InfoChatBox() {
    if (UIStates.chatIndex.value >= 0 && UIStates.mainListUsers.value.isNotEmpty()) {
        Image(
            UIStates.mainListUsers.value[UIStates.chatIndex.value].iconUri, width = 35, height = 35,
            modifier = Modifier.border(1.px, LineStyle.Outset, color = Colors.Black)
                .borderRadius(30.px).margin(left = 7.px).margin(left = 40.px)
        )
        P({
            style {
                paddingBottom(15.px)
                paddingLeft(90.px)
            }
        }) {
            B({
                style {
                    color(Colors.White)
                }
            }) {
                Box(modifier = Modifier.width(100.px)) {
                    Text(
                        UIStates.mainListUsers.value[UIStates.chatIndex.value]
                        .name.ifEmpty { UIStates.mainListUsers.value[UIStates.chatIndex.value].id })
                }
            }
        }
        P({
            style {
                paddingTop(15.px)
                paddingLeft(90.px)
                color(Colors.White)
            }
        }) {
            Small {
                SpanText(if (UIStates.mainListUsers.value[UIStates.chatIndex.value].onLine != "0")
                    "в сети" else "не в сети")
            }
        }
        Box(
            Modifier.fillMaxWidth().padding(right = 40.px),
            contentAlignment = Alignment.CenterEnd
        ) {
            FaDeleteLeft(modifier = Modifier.onClick {
                deleteAllMessages()
            }.color(Colors.White))
        }
    }
}