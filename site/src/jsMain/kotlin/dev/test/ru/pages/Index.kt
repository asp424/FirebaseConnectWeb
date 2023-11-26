package dev.test.ru.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.varabyte.kobweb.core.Page
import dev.test.ru.data.setAuthStateListener
import dev.test.ru.states.UIStates.mainScreenIsVisible
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Progress

@Page
@Composable
fun HomePage() {
    LaunchedEffect(Unit) {
        setAuthStateListener {
            mainScreenIsVisible.value = it
        }
    }
    when (mainScreenIsVisible.value) {
        "1" -> RegScreen()
        "2" -> MainScreen()
        "0" -> ProgressScreen()

    }
}
