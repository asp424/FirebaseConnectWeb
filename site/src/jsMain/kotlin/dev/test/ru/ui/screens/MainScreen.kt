package dev.test.ru.ui.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.ru.data.sources.chatJob
import dev.test.ru.data.sources.mainListJob
import dev.test.ru.data.sources.signOut
import dev.test.ru.data.sources.startMainListListener
import dev.test.ru.ui.states.UIStates.chatMessages
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.cells.ChatBox
import dev.test.ru.ui.cells.Header
import dev.test.ru.ui.cells.UserCardS
import dev.test.ru.ui.states.UIStates.screenHeight
import dev.test.ru.ui.states.UIStates.screenWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun MainScreen() {
    val coroutine = rememberCoroutineScope()
    var settingsIsVisible by remember {
        mutableStateOf(false)
    }
    var isExpandedMain by remember {
        mutableStateOf(false)
    }
    var isExpandedColumn by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        startMainListListener()
    }
    var isBigMessages by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(mainListUsers.value) {
        if (mainListUsers.value.isNotEmpty() && !isExpandedColumn) {
            delay(500)
            isExpandedColumn = true
        }
    }

    Row(Modifier.fillMaxWidth().padding(top = 40.px), Arrangement.Center) {
        UserCardS(isBigMessages, onEndAnim = { if (!isExpandedMain) isExpandedMain = true }) {
            if (mainListJob.isActive) isBigMessages = true
        }
        ChatBox(isExpandedMain, {

        })
         {
            settingsIsVisible = false
            chatMessages.value = emptyList()
        }
        SettingsScreen(settingsIsVisible) {
            coroutine.launch {
                isExpandedMain = false
                mainListJob.cancel()
                chatJob.cancel()
                delay(1000)
                isBigMessages = false
            }
        }
    }
    Header { settingsIsVisible = !settingsIsVisible }
}
