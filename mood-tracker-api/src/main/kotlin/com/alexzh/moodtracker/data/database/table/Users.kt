package com.alexzh.moodtracker.data.database.table

import org.jetbrains.exposed.sql.Table

object UsersTable : Table(name = "users") {
    val id = long("id").autoIncrement()
    val username = varchar("username", length = 45)
    val email = varchar("email", length = 45)
    val password = text("password")
}