package dev.test.ru.ui.states

import androidx.compose.runtime.mutableStateOf
import dev.test.ru.data.models.MessageModel
import dev.test.ru.data.models.UserModel
import kotlinx.browser.window

object UIStates {
    var mainListUsers = mutableStateOf(listOf<UserModel>())
    var chatMessages = mutableStateOf(listOf<MessageModel>())
    var encodedText = mutableStateOf("ass")
    var chatIndex = mutableStateOf(-1)
    var mainScreenIsVisible = mutableStateOf("0")
    var mainProgressIsVisible = mutableStateOf(true)
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var myDigit = ""
    var chatUserDigit = ""
    var screenWidth = window.innerWidth
    var screenHeight = window.innerHeight
}