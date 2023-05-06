package com.alexzh.moodtracker.model.response

import com.alexzh.moodtracker.data.model.UserInfo
import io.ktor.http.*

sealed class UserResponse(val status: HttpStatusCode) {

    class Success(
        val userInfo: UserInfo
    ): UserResponse(HttpStatusCode.OK)

    class Error(
        val message: String
    ): UserResponse(HttpStatusCode.BadRequest)

    class Unauthorized(
        val message: String
    ): UserResponse(HttpStatusCode.Unauthorized)
}