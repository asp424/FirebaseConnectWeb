package dev.test.ru.ui.cells.chat

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.test.ru.data.sources.deleteAllMessages
import org.jetbrains.compose.web.css.px

@Composable
fun deleteIcon() =
    Box(Modifier.fillMaxWidth().padding(right = 40.px), Alignment.CenterEnd) {
        Image("del.svg", modifier = Modifier.onClick { deleteAllMessages() })
    }
