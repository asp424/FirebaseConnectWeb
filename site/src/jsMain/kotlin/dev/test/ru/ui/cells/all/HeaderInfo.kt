package dev.test.ru.ui.cells.all

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaIcons
import dev.test.ru.ui.states.UIStates
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Text

@Composable
fun headerInfo(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().border(1.px, LineStyle.Groove, Colors.White)
            .height(47.px).background(Colors.CadetBlue).padding(right = 50.px),
        Arrangement.SpaceBetween, Alignment.CenterVertically
    ) {

        Ol({ style { color(Colors.White) } }) { Text("Kilogram v.1.0") }
        Ol({ style { color(Colors.White); marginRight(120.px) } }) { Text(UIStates.encodedText.value) }
        FaIcons(modifier = Modifier.onClick { onClick() }.color(Colors.White))
    }
}