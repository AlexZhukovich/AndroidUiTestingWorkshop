package com.alexzh.moodtracker.auth

import com.alexzh.moodtracker.data.model.User
import com.auth0.jwt.JWTVerifier

interface JwtService {
    val claim: String

    val verifier: JWTVerifier

    fun generateToken(user: User): String
}