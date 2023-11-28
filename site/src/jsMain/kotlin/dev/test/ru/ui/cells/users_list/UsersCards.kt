package dev.test.ru.ui.cells.users_list

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import dev.test.ru.data.sources.signOut
import dev.test.ru.data.sources.startMessagesListener
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.chatIndex
import dev.test.ru.ui.states.UIStates.chatMessages
import dev.test.ru.ui.states.UIStates.chatUserDigit
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import dev.test.scrollLogicMainListUsers
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun userCards(isBig: Boolean, onEndAnim: () -> Unit, onLastCardHandled: () -> Unit) {

    val y = remember { mutableStateOf(6.0) }

    val width = animateDpAsState(if (isBig) 270.dp else 0.dp, tween(500)) {
        if (it == 270.dp) onEndAnim() else { mainListUsers.value = emptyList(); signOut() }
    }.value.value.px

    Div({ onWheel { it.scrollLogicMainListUsers(y) } }) {

        Column(Modifier.margin(top = y.value.px)) {

            mainListUsers.value.forEachIndexed { i, m ->

                if (i == mainListUsers.value.size - 1) {
                    mainProgressIsVisible.value = false
                    if (!isBig && width == 0.px) onLastCardHandled()
                }

                if (m.id != "chats" && m.id != UIStates.myDigit) userCard(m, width) {
                    chatUserDigit = m.id
                    chatIndex.value = i
                    startMessagesListener { chatMessages.value = this }
                }
            }
        }
    }
}
