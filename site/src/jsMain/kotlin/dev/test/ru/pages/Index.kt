package dev.test.ru.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.Page
import dev.test.ru.data.sources.setAuthStateListener
import dev.test.ru.ui.screens.MainScreen
import dev.test.ru.ui.screens.progressScreen
import dev.test.ru.ui.screens.RegScreen
import dev.test.ru.ui.states.UIStates.mainScreenIsVisible

@Page
@Composable
fun HomePage() {

    LaunchedEffect(true) { setAuthStateListener { mainScreenIsVisible.value = it } }

    when (mainScreenIsVisible.value) {
        "1" -> RegScreen()
        "2" -> MainScreen()
    }
    progressScreen()
}
