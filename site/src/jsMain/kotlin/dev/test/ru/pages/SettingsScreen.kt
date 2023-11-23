package dev.test.ru.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import dev.test.ru.data.signOut
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun SettingsScreen() {
    Box(Modifier.fillMaxSize().padding(bottom = 20.px), contentAlignment = Alignment.BottomCenter) {
        Button({
            onClick {
                signOut()
            }
        }) {
            Text("Выход")
        }
    }
}