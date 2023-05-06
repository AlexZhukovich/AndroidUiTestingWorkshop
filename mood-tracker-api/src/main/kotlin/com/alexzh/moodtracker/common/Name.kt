package com.alexzh.moodtracker.common

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Name(
    val value: String
) {
    val length: Int
        get() = value.length

    fun isValid(): Boolean {
        val pattern = "[a-zA-Z0-9_.]+".toRegex()
        return value.matches(pattern)
    }
}