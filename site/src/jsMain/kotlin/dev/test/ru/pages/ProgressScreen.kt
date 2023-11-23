package dev.test.ru.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import org.jetbrains.compose.web.dom.Progress

@Composable
fun ProgressScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Progress { }
    }
}