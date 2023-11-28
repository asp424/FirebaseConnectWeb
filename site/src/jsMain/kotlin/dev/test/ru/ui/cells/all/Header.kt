package dev.test.ru.ui.cells.all

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import dev.test.ru.ui.cells.chat.chatIdText
import dev.test.ru.ui.cells.chat.deleteIcon
import dev.test.ru.ui.cells.chat.userIcon
import dev.test.ru.ui.cells.chat.userStatusText
import dev.test.ru.ui.states.UIStates.isShowAbleInfoBox
import dev.test.ru.ui.states.UIStates.screenWidth
import org.jetbrains.compose.web.css.px

@Composable
fun header(onClick: () -> Unit) {

    headerInfo(onClick)

    Box(
        modifier = Modifier.width(865.px).margin(left = ((screenWidth / 2) - 410).px).height(45.px),
        Alignment.CenterStart
    ) {
        if (isShowAbleInfoBox) {
            userIcon(); chatIdText(); userStatusText(); deleteIcon()
        }
    }
}