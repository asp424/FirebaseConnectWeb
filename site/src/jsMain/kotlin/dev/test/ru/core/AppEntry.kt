package dev.test.ru.core

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.ScrollSnapStop
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightGray
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.breakpoint.BreakpointUnitValue
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import dev.test.ru.ui.states.UIStates.screenHeight
import dev.test.ru.ui.states.UIStates.screenWidth
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.events.Event

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
        js("document.body.style.overflow = 'hidden'")
    SilkApp {
        Surface(SmoothColorStyle.toModifier().minHeight(100.vh).background(LightGray)
        ) {
            content()
        }
    }
}
