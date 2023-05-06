package com.alexzh.moodtracker.plugins

import com.alexzh.moodtracker.auth.JwtService
import com.alexzh.moodtracker.auth.principal.UserPrincipal
import com.alexzh.moodtracker.data.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureAuthentication() {
    val jwtService: JwtService by inject(JwtService::class.java)
    val userRepository: UserRepository by inject(UserRepository::class.java)

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            validate {
                val userId = it.payload.getClaim(jwtService.claim).asLong()

                val user = userRepository.getUserById(userId)
                if (user != null) UserPrincipal(user) else null
            }
        }
    }
}
