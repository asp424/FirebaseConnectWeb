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
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun settingsScreen(settingsIsVisible: Boolean, onSignOut: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().maxWidth(223.px)
            .height(
                animateDpAsState(
                    if (settingsIsVisible) (screenHeight - 41).dp else 0.dp, tween(700)
                ).value.value.px
            )
            .border(if (settingsIsVisible) 1.px else 0.px, LineStyle.Inset, Colors.Black)
            .background(Colors.White)
            .borderRadius(bottomRight = 10.px, bottomLeft = 10.px), Alignment.TopCenter
    ) {
        Box(Modifier.fillMaxSize().padding(bottom = 20.px), Alignment.BottomCenter) {

            Button({ onClick { onSignOut() }
            }) { Text("Выход") }
        }
    }
}