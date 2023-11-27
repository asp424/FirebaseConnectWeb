package dev.test.ru.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.Page
import dev.test.ru.data.sources.setAuthStateListener
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import dev.test.ru.ui.screens.MainScreen
import dev.test.ru.ui.screens.ProgressScreen
import dev.test.ru.ui.screens.RegScreen
import dev.test.ru.ui.states.UIStates.mainScreenIsVisible
import kotlinx.browser.document
import org.w3c.dom.events.Event

@Page
@Composable
fun HomePage() {
    LaunchedEffect(Unit) {
        setAuthStateListener {
            mainScreenIsVisible.value = it
        }
    }
    when (mainScreenIsVisible.value) {
        "1" -> {
            mainProgressIsVisible.value = false
            RegScreen()
        }
        "2" -> MainScreen()
    }
    if (mainProgressIsVisible.value) ProgressScreen()
}
