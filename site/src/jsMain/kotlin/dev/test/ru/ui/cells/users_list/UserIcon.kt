package dev.test.ru.ui.cells.users_list

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@Composable
fun userIcon(iconUri: String) = Image(
    iconUri, Modifier.border(1.px, LineStyle.Outset, color = Colors.Black)
        .borderRadius(30.px).margin(left = 7.px), width = 35, height = 35,
    )
