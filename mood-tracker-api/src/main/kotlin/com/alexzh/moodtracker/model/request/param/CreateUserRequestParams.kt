package com.alexzh.moodtracker.model.request.param

import com.alexzh.moodtracker.common.Email
import com.alexzh.moodtracker.common.Name
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestParams(
    val username: Name,
    val email: Email,
    val password: String
)
