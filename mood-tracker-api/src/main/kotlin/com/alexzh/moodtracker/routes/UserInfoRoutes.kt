package com.alexzh.moodtracker.routes

import com.alexzh.moodtracker.auth.principal.UserPrincipal
import com.alexzh.moodtracker.data.model.UserInfo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userInfoRoutes() {
    route("/api/v1/users") {
        authenticate("jwt") {
            get("/me") {
                val userPrincipal = call.principal<UserPrincipal>()
                if (userPrincipal != null) {
                    call.respond(HttpStatusCode.OK, UserInfo(userPrincipal.user.name, userPrincipal.user.email))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Not Authorized")
                }
            }
        }
    }
}