package dev.test.ru.states

import androidx.compose.runtime.mutableStateOf
import dev.gitlive.firebase.database.DataSnapshot
import dev.test.ru.models.UserModel

object UIStates {
    var firebaseData = mutableStateOf(listOf<UserModel>())
    var getStatus = mutableStateOf("")
}