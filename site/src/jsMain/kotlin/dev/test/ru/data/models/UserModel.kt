package dev.test.ru.data.models

import dev.test.ru.data.models.Locations

data class UserModel(
    val name: String = "",
    val token: String = "",
    val id: String = "",
    val onLine: String = "0",
    val isWriting: String = "",
    val iconUri: String = "",
    val lastMessage: String = "",
    val isFree: Boolean = true,
    val users: String = "",
    val locations: Locations = Locations()
)






