package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.borderRadius
import dev.test.ru.data.models.MessageModel
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun textMessage(message: MessageModel) {
    with(message) {
        Box(
            Modifier.fillMaxWidth().padding(left = 30.px, right = 30.px).margin(bottom = 5.px),
            alignment
        ) {

            Row(
                Modifier.background(Colors.Silver).padding(5.px, 10.px, 0.px, 10.px)
                    .borderRadius(alignment, 15.px)
            ) {

                Box(Modifier.maxWidth(350.px).padding(bottom = 8.px), Alignment.Center)
                { Text(text) }

                Box(
                    Modifier.padding(left = 4.px).scale(0.6).margin(top = 8.px),
                    Alignment.BottomCenter
                ) { Text("00:00") }
            }
        }
    }
}