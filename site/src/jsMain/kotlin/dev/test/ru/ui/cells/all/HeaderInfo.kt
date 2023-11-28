package dev.test.ru.ui.cells.all

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaIcons
import dev.test.ru.ui.states.UIStates.myIdText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Text

@Composable
fun headerInfo(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().border(1.px, LineStyle.Groove, White)
            .height(47.px).background(CadetBlue).padding(right = 50.px),
        Arrangement.SpaceBetween, Alignment.CenterVertically
    ) {

        Ol({ style { color(White) } }) { Text("Kilogram v.1.0") }
        Ol({ style { color(White); marginRight(120.px) } }) { Text(myIdText.value) }
        FaIcons(Modifier.onClick { onClick() }.color(White))
    }
}