package com.alexzh.moodtracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val username: String,
    val email: String
)
