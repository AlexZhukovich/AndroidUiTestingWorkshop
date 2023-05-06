package com.alexzh.moodtracker.model.request.param

import com.alexzh.moodtracker.common.Email
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestParams(
    val email: Email,
    val password: String
)
