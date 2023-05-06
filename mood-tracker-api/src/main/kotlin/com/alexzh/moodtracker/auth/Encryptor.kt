package com.alexzh.moodtracker.auth

import javax.crypto.spec.SecretKeySpec

interface Encryptor {
    val keySpec: SecretKeySpec

    fun encrypt(value: String): String
}