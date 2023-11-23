package dev.test.ru.data.models

enum class LoadImageStates(val value: String) {
    START_UPLOAD("<*IL>startUpload<*/IL>"),
    UPLOADED("<*IL>uploaded<*/IL>"),
    LOADING("<*IL>loading<*/IL>"),
    ENCRYPTED("<*IL>encrypted<*/IL>"),
    NULL("<*IL>null<*/IL>")
}