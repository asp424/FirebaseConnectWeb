package dev.test.ru.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.Page
import dev.test.ru.data.sources.ass
import dev.test.ru.data.sources.setAuthStateListener
import dev.test.ru.ui.screens.MainScreen
import dev.test.ru.ui.screens.RegScreen
import dev.test.ru.ui.screens.progressScreen
import dev.test.ru.ui.states.UIStates.mainScreenIsVisible
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

@Page
@Composable
fun HomePage() {

    LaunchedEffect(true) { setAuthStateListener { mainScreenIsVisible.value = it } }

    when (mainScreenIsVisible.value) {
        "1" -> RegScreen()
        "2" -> MainScreen()
    }
    progressScreen()

    LaunchedEffect(true) {
        CoroutineScope(Default).launch {
            ass()
        }
    }
}
