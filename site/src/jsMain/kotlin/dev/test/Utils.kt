package dev.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.web.events.SyntheticWheelEvent
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.dom.Div

fun Modifier.borderRadius(alignment: Alignment, radius: CSSNumeric) =
    if (alignment == Alignment.CenterStart)
        borderRadius(topRight = radius, bottomRight = radius, bottomLeft = radius)
    else borderRadius(topRight = radius, bottomLeft = radius, topLeft = radius)
@Composable
fun div(
    styleProperty: String = "",
    stylePropertyValue: String = "",
    onWheel: (SyntheticWheelEvent) -> Unit,
    content: @Composable () -> Unit
) = Div({ style { property(styleProperty, stylePropertyValue) }; onWheel { onWheel(it) } })
{ content() }

fun SyntheticWheelEvent.scrollLogicMainListUsers(y: MutableState<Double>) {
    if (y.value > 6) y.value = 6.0
    if (mainListUsers.value.size >= 11) {
        val outScreenCards = mainListUsers.value.size - 13
        val maxNegativeY = ((screenHeight - 37) / 11) * outScreenCards
        if (y.value <= 6 && y.value >= (6 - maxNegativeY)) y.value -= deltaY / 6
        if (y.value < (6 - maxNegativeY)) y.value = (6 - maxNegativeY).toDouble()
        if (y.value > 6) y.value = 6.0
    } else {
        if (y.value <= 5) y.value -= deltaY / 4
    }
    if (y.value > 6) y.value = 6.0
}
