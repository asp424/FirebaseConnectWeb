package dev.test.ru.core

import dev.test.ru.data.sources.ERROR
external fun require(module: String): dynamic

class Crypto {

    fun encrypt(normal: String): String {
        val cryptoJS = require("crypto-js")
        val key = cryptoJS.enc.Base64.parse("b/Gu5posvwDsXjfirtaq4g==")
        val iv = cryptoJS.enc.Base64.parse("5D9r9ZVzEYYgha93/aUK2w==")
        return js("cryptoJS.AES.encrypt(normal, key, { iv: iv}).toString()") as String
    }

    private fun decrypt(encrypted: String): String {
        val cryptoJS = require("crypto-js")
        val key = cryptoJS.enc.Base64.parse("b/Gu5posvwDsXjfirtaq4g==")
        val iv = cryptoJS.enc.Base64.parse("5D9r9ZVzEYYgha93/aUK2w==")
        return js(
            "cryptoJS.enc.Utf8.stringify(cryptoJS.AES.decrypt(" +
                    "{ciphertext: cryptoJS.enc.Base64.parse(encrypted)}, key, { iv: iv }))"
        ) as String
    }

    fun String.decrypting() = if (this != ERROR && isNotEmpty())
        decrypt(this)
    else "Сообщений пока нет"
}