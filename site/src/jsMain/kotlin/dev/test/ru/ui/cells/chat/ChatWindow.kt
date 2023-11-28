package dev.test.ru.ui.cells.chat

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.BoxScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightYellow
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.LineStyle.Companion.Groove
import org.jetbrains.compose.web.css.px

@Composable
fun chatWindow(
    isExpandedMain: Boolean,
    onInvisibleChatBox: () -> Unit,
    onEndAnim: () -> Unit,
    content: @Composable (BoxScope) -> Unit
) {
    Box(
        Modifier.height(
            animateIntAsState(
                if (isExpandedMain) screenHeight - 41 else 0, tween(500)
            )
            { if (it == 0) onInvisibleChatBox() else onEndAnim() }.value.px
        ).fillMaxWidth()
            .background(LightYellow).border(1.px, Groove, Black)
            .borderRadius(bottomRight = 10.px, bottomLeft = 10.px), Alignment.TopCenter
    ) { content(this) }
}