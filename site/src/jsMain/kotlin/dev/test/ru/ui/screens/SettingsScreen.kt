package dev.test.ru.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.ru.data.sources.signOut
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun SettingsScreen(settingsIsVisible: Boolean, onSignOut: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().maxWidth(223.px)
            .height(
                animateDpAsState(
                    if (settingsIsVisible) (screenHeight - 41).dp else 0.dp, tween(700)
                ).value.value.px
            )
            .border(if (settingsIsVisible) 1.px else 0.px, LineStyle.Inset, Colors.Black)
            .background(Colors.White)
            .borderRadius(bottomRight = 10.px, bottomLeft = 10.px),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(Modifier.fillMaxSize().padding(bottom = 20.px), contentAlignment = Alignment.BottomCenter) {
            Button({
                onClick {
                    onSignOut()
                }
            }) {
                Text("Выход")
            }
        }
        Box(
            modifier = Modifier.height(
                animateDpAsState(
                    if (settingsIsVisible) 57.dp else 0.dp, tween(700)
                ).value.value.px
            ).fillMaxWidth()
                .border(1.px, LineStyle.Outset, Colors.Black)
                .borderRadius(bottomRight = 10.px, bottomLeft = 10.px)
                .background(Colors.CadetBlue),
            contentAlignment = Alignment.Center
        )
    }
}