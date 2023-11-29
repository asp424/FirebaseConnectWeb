package dev.test.ru.data

class FirebaseParser {

    fun String.getName() = substringAfter(D_T_E).substringBefore(T_T_S)

    fun String.parseDigit() = substringAfter(D_T_S).substringBefore(D_T_E)

    fun String.removeKey() = substringAfter("):")

    fun String.getReplyKey() = substringAfter(R_T_S).substringBefore(R_T_E)

    fun String.getText() = substringAfter(T_T_E).substringBefore(R_T_S)

    fun String.getVoiceTimestamp() = substringAfter(IS_RECORD).substringBefore(R_T_S)

    fun String.getLocalImageUri() = substringAfter(IS_IMAGE).substringBefore(T_I_S)

    fun String.parseTimestamp() = substringAfter(T_T_S).substringBefore(T_T_E)

}