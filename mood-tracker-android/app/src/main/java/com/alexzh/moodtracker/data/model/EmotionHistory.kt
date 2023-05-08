package com.alexzh.moodtracker.data.model

import java.time.ZonedDateTime

data class EmotionHistory(
    val id: Long,
    val date: ZonedDateTime,
    val emotion: Emotion,
    val activities: List<Activity>,
    val note: String?
)