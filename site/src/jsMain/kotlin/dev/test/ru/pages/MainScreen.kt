package dev.test.ru.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightGrey
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.ArrowForwardIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaIcons
import dev.test.ru.data.startMainListListener
import dev.test.ru.data.startMessagesListener
import dev.test.ru.models.UserCard
import dev.test.ru.states.UIStates.chatMessages
import dev.test.ru.states.UIStates.chatUserDigit
import dev.test.ru.states.UIStates.encodedText
import dev.test.ru.states.UIStates.mainListUsers
import dev.test.ru.states.UIStates.myDigit
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.LineStyle.Companion.Groove
import org.jetbrains.compose.web.css.LineStyle.Companion.Inset
import org.jetbrains.compose.web.css.LineStyle.Companion.Outset
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput

@Composable
fun MainScreen(onClick: () -> Unit = {}) {
    var textMessage by remember {
        mutableStateOf("")
    }
    var settingsIsVisible by remember {
        mutableStateOf(false)
    }
    var isExpandedMain by remember {
        mutableStateOf(false)
    }
    var isExpandedColumn by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        startMainListListener()
    }
    LaunchedEffect(mainListUsers.value) {
        if (mainListUsers.value.isNotEmpty() && !isExpandedColumn) {
            delay(500)
            isExpandedColumn = true
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(LightGrey))

    Row(Modifier.fillMaxWidth().padding(top = 40.px), horizontalArrangement = Arrangement.Center) {

        Column(modifier = Modifier.width(230.px)) {
            mainListUsers.value.forEachIndexed { i, m ->
                if (i == mainListUsers.value.size - 1) {
                    if (!isExpandedMain) isExpandedMain = true
                }
                if (m.id != "chats" && m.id != myDigit) UserCard(m) {
                    chatUserDigit = m.id
                    startMessagesListener {
                        chatMessages.value = this
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(
                    animateDpAsState(
                        if (isExpandedMain) 570.dp else 0.dp, tween(700)
                    ).value.value.px
                )
                .background(White)
                .border(1.px, Groove, Black)
                .borderRadius(bottomRight = 10.px, bottomLeft = 10.px),
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier.height(
                    animateDpAsState(
                        if (isExpandedMain) 50.dp else 0.dp, tween(700)
                    ).value.value.px
                ).fillMaxWidth()
                    .border(1.px, Outset, Black)
                    .borderRadius(bottomRight = 10.px, bottomLeft = 10.px)
                    .background(CadetBlue),
                contentAlignment = Alignment.Center
            )
            Box(
                modifier = Modifier.fillMaxHeight().padding(bottom = 30.px),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(left = 40.px, right = 40.px)
                ) {

                    TextInput(textMessage) {
                        style {
                            maxWidth(325.px)
                            height(30.px)
                        }
                        onInput { textMessage = it.value }
                    }
                    ArrowForwardIcon(modifier = Modifier.size(40.px).onClick {

                    })
                }
            }
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxHeight()
                    ) {
                    chatMessages.value.forEach {
                        Text(it.text)
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().maxWidth(223.px)
                .height(
                    animateDpAsState(
                        if (settingsIsVisible) 570.dp else 0.dp, tween(700)
                    ).value.value.px
                )
                .border(if (settingsIsVisible) 1.px else 0.px, Inset, Black)
                .background(White)
                .borderRadius(bottomRight = 10.px, bottomLeft = 10.px),
            contentAlignment = Alignment.CenterEnd
        ) {
            SettingsScreen()
        }

    }
    Row(
        modifier = Modifier.fillMaxWidth().border(1.px, Groove, White)
            .height(30.px).background(CadetBlue).padding(20.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Ol({ style { color(White) } }) { Text("Kilogram") }
        Ol({ style { color(White) } }) { Text(encodedText.value) }
        FaIcons(modifier = Modifier.onClick {
            settingsIsVisible = !settingsIsVisible
        }.color(White))
    }
}
