package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtrackerdb.DayToHappinessLevel
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface EmotionHistoryRepository {

    suspend fun getDayToAverageHappinessLevel(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): List<DayToHappinessLevel>

    fun getEmotionsHistoryByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): Flow<Result<List<EmotionHistory>>>

    fun getEmotionHistoryById(
        id: Long
    ): Flow<Result<EmotionHistory?>>

    suspend fun getEmotions(): List<Emotion>

    suspend fun getActivities(): List<Activity>

    suspend fun insertOrUpdateEmotionHistory(
        id: Long?,
        date: ZonedDateTime,
        emotionId: Long,
        selectedActivityIds: List<Long>,
        note: String?,
    )

    suspend fun deleteEmotionHistory(
        emotionHistory: EmotionHistory
    )
}