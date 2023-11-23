package dev.test.ru.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.varabyte.kobweb.core.Page
import dev.test.ru.data.setAuthStateListener
import dev.test.ru.states.UIStates.mainScreenIsVisible
import org.jetbrains.compose.web.dom.Progress

@Page
@Composable
fun HomePage() {
    setAuthStateListener{
        mainScreenIsVisible.value = it
    }
    when(mainScreenIsVisible.value){
        "1" -> RegScreen()
        "2"  -> MainScreen()
       // else -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
       //     Progress {  }
     //   }
    }
}
