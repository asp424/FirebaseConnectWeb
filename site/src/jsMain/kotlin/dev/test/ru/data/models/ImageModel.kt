package dev.test.ru.data.models

data class ImageModel(
    val imageLocalUri: String = "",
    val uploadState: LoadImageStates = LoadImageStates.NULL,
    val key: String = "",
    var isShow: Boolean = true
)