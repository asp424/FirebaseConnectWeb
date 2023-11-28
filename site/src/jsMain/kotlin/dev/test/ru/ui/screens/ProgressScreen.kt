package dev.test.ru.ui.screens

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.mainProgressIsVisible
import org.jetbrains.compose.web.dom.Progress

@Composable
fun progressScreen() {
    if (mainProgressIsVisible.value)
    Box(Modifier.fillMaxSize(), Alignment.Center) { Progress { } }
}
