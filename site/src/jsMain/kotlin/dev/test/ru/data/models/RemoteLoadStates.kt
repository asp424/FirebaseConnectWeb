package dev.test.ru.data.models

import dev.gitlive.firebase.database.DataSnapshot

sealed interface RemoteLoadStates {
    data class Success(val data: DataSnapshot) : RemoteLoadStates
    data class Failure<T>(val data: T) : RemoteLoadStates
}