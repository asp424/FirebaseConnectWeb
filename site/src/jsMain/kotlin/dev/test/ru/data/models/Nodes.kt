package dev.test.ru.data.models


sealed interface Nodes{
    object MESSAGES: Nodes
    object CHATS: Nodes
    object ONLINE: Nodes
    object WRITING: Nodes
    object TOKEN: Nodes
    object NOTIFY: Nodes
    object HIM: Nodes
    object MY: Nodes
    object CALL: Nodes
    object LAST: Nodes
    object NAME: Nodes
    object ICON: Nodes

    object USERS: Nodes

    object LOC: Nodes

    fun node() = when(this){
        MESSAGES -> "messages"
        ONLINE -> "online"
        WRITING -> "writing"
        TOKEN -> "token"
        NOTIFY -> "notify"
        CHATS -> "chats"
        HIM -> "himDigit"
        MY -> "myDigit"
        CALL -> "call"
        LAST -> "last"
        NAME -> "name"
        ICON -> "icon"
        USERS -> "users"
        LOC -> "location"
    }
}