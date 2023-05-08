package com.alexzh.moodtracker.data.model

data class Emotion(
    val id: Long,
    val name: String,
    val happinessLevel: Int,
    val icon: String
)