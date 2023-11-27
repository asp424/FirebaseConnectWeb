package dev.test.ru.ui.cells

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaIcons
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.screenWidth
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Text

@Composable
fun Header(onClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().border(1.px, LineStyle.Groove, White)
            .height(40.px).background(CadetBlue).padding(right = 50.px, top = 25.px, bottom = 25.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Ol({ style { color(White) } }) { Text("Kilogram v.1.0") }
        Ol({ style { color(White); marginRight(120.px) } }) { Text(UIStates.encodedText.value) }
        FaIcons(modifier = Modifier.onClick{
            onClick()
        }.color(White))
    }
    Box(modifier = Modifier.width(865.px).margin(left = ((screenWidth / 2) - 410).px)
        .height(45.px), contentAlignment = Alignment.CenterStart) {
        InfoChatBox()
    }
}