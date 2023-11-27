package dev.test.ru.ui.cells

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.ru.data.sources.signOut
import dev.test.ru.data.sources.startMessagesListener
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.chatIndex
import dev.test.ru.ui.states.UIStates.chatMessages
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun UserCardS(isBig: Boolean, onEndAnim: () -> Unit, onLastCardHandled: () -> Unit) {
    var y by remember {
        mutableStateOf(6.0)
    }
    val width = animateDpAsState(
        if (isBig) 270.dp else 0.dp, tween(500)
    ) {
        if (it == 270.dp) onEndAnim() else {
            mainListUsers.value = emptyList()
            signOut()
        }
    }.value.value.px
    Div({

        onWheel {
            if (y > 6) y = 6.0
            if (mainListUsers.value.size >= 11) {
                val outScreenCards = mainListUsers.value.size - 12
                val maxNegativeY = ((screenHeight - 37) / 11) * outScreenCards
                if(y <= 6 && y >= (6 - maxNegativeY)) y -= it.deltaY / 6
                if (y < (6 - maxNegativeY)) y = (6 - maxNegativeY).toDouble()
                if (y > 6) y = 6.0
            } else { if (y <= 5) y -= it.deltaY / 4 }
        }
    }) {
        Column(
            modifier = Modifier.margin(top = y.px).fillMaxHeight()
        ) {
            mainListUsers.value.forEachIndexed { i, m ->
                if (i == mainListUsers.value.size - 1) {
                    mainProgressIsVisible.value = false
                    if (!isBig && width == 0.px) onLastCardHandled()
                }
                if (m.id != "chats" && m.id != UIStates.myDigit
                ) UserCard(m, width) {
                    UIStates.chatUserDigit = m.id
                    chatIndex.value = i
                    startMessagesListener {
                        chatMessages.value = this
                    }
                }
            }
        }
    }
}