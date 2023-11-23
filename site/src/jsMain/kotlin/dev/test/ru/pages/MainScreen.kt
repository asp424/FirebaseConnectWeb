package dev.test.ru.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.icons.ArrowForwardIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaIcons
import dev.test.ru.data.ass
import dev.test.ru.data.encodeText
import dev.test.ru.data.startMainListListener
import dev.test.ru.models.UserCard
import dev.test.ru.states.UIStates.encodedText
import dev.test.ru.states.UIStates.mainListUsers
import org.jetbrains.compose.web.css.LineStyle.Companion.Groove
import org.jetbrains.compose.web.css.LineStyle.Companion.Inset
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
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
    LaunchedEffect(true) {
        ass()
        startMainListListener()
    }
    Box(modifier = Modifier.fillMaxSize().background(LightGrey))

    Row(Modifier.fillMaxWidth().padding(top = 40.px), horizontalArrangement = Arrangement.Center) {

        Column() {
            mainListUsers.value.forEach { if (it.id != "chats") UserCard(it, onClick) }
        }
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(570.px)
                        .background(White)
                        .border(1.px, Groove, Black)
                        .borderRadius(bottomRight = 10.px, bottomLeft = 10.px),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier.height(40.px).fillMaxWidth()
                            .borderRadius(bottomRight = 10.px, bottomLeft = 10.px)
                            .background(CadetBlue),
                        contentAlignment = Alignment.Center
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().padding(bottom = 30.px),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                TextInput(textMessage) {
                                    style {
                                        width(325.px)
                                        height(30.px)
                                    }
                                    onInput { textMessage = it.value }
                                }
                                ArrowForwardIcon(modifier = Modifier.size(40.px).onClick {

                                })
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
                .borderRadius(bottomRight = 10.px, bottomLeft = 10.px), contentAlignment = Alignment.CenterEnd
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
        Text(encodedText.value)
        FaIcons(modifier = Modifier.onClick {
            settingsIsVisible = !settingsIsVisible
        }.color(White))
    }
}
