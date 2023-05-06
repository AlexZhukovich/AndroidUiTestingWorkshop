package com.alexzh.moodtracker.routes

import com.alexzh.moodtracker.controller.AuthController
import com.alexzh.moodtracker.model.request.param.CreateUserRequestParams
import com.alexzh.moodtracker.model.request.param.LoginRequestParams
import com.alexzh.moodtracker.model.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(
    authController: AuthController
) {
    route("/api/v1/users") {
        post("/create") {
            val params = runCatching { call.receive<CreateUserRequestParams>() }.getOrElse {
                throw BadRequestException("The 'username', 'email' and 'password' parameters are required")
            }

            when (val response = authController.createUser(params)) {
                is AuthResponse.Created -> call.respond(response.status, response.token)
                is AuthResponse.Error -> call.respond(response.status, response.message)
                is AuthResponse.Success -> return@post
            }
        }

        post("/login") {
            val params = runCatching { call.receive<LoginRequestParams>() }.getOrElse {
                throw BadRequestException("The 'email' and 'password' parameters are required")
            }

            when (val response = authController.login(params)) {
                is AuthResponse.Created -> return@post
                is AuthResponse.Success ->
                    if (response.token != null) {
                        call.respond(response.status, response.token)
                    } else call.respond(response.status)
                is AuthResponse.Error -> call.respond(response.status, response.message)
            }
        }
    }
}