package com.alexzh.moodtracker.auth.principal

import com.alexzh.moodtracker.data.model.User
import io.ktor.server.auth.*

class UserPrincipal(
    val user: User
): Principal
