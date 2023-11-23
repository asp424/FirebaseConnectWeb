package dev.test.ru.states

import androidx.compose.runtime.mutableStateOf
import dev.test.ru.data.models.MessageModel
import dev.test.ru.models.UserModel

object UIStates {
    var mainListUsers = mutableStateOf(listOf<UserModel>())
    var chatMessages = mutableStateOf(listOf<MessageModel>())
    var getStatus = mutableStateOf("")
    var encodedText = mutableStateOf("ass")
    var mainScreenIsVisible = mutableStateOf("0")
    var myDigit = ""
}