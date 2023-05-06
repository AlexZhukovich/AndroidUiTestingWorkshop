package com.alexzh.moodtracker.data.model

data class User(
    val id: Long = -1L,
    val name: String,
    val email: String,
    val passwordHash: String
)
