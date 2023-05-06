package com.alexzh.moodtracker.auth

import com.alexzh.moodtracker.data.model.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JwtServiceImpl(
    secretKey: String
): JwtService {
    private val algorithm = Algorithm.HMAC512(secretKey)
    private val issuer = "mood-tracker-server"

    override val claim = "id"

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    override fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim(claim, user.id)
            .sign(algorithm)
    }
}