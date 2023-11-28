package dev.test.ru.ui.screens

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.test.ru.data.sources.chatJob
import dev.test.ru.data.sources.mainListJob
import dev.test.ru.data.sources.startMainListListener
import dev.test.ru.ui.states.UIStates.chatMessages
import dev.test.ru.ui.states.UIStates.mainListUsers
import dev.test.ru.ui.cells.chat.chatBox
import dev.test.ru.ui.cells.all.header
import dev.test.ru.ui.cells.users_list.userCards
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px

@Composable
fun MainScreen() {

    val coroutine = rememberCoroutineScope()

    var settingsIsVisible by remember { mutableStateOf(false) }

    var isExpandedMain by remember { mutableStateOf(false) }

    var isExpandedColumn by remember { mutableStateOf(false) }

    LaunchedEffect(true) { startMainListListener() }

    var isBigMessages by remember { mutableStateOf(false) }

    LaunchedEffect(mainListUsers.value) {
        if (mainListUsers.value.isNotEmpty() && !isExpandedColumn) {
            delay(500)
            isExpandedColumn = true
        }
    }

    Row(Modifier.fillMaxWidth().padding(top = 40.px), Arrangement.Center) {

        userCards(isBigMessages, onEndAnim = { if (!isExpandedMain) isExpandedMain = true }) {

            if (mainListJob.isActive) isBigMessages = true
        }
            chatBox(isExpandedMain) {
                settingsIsVisible = false
                chatMessages.value = emptyList()
            }

            settingsScreen(settingsIsVisible) {
                coroutine.launch {
                    isExpandedMain = false
                    mainListJob.cancel()
                    chatJob.cancel()
                    delay(1000)
                    isBigMessages = false
                }
            }
        }

    header { settingsIsVisible = !settingsIsVisible }
}
