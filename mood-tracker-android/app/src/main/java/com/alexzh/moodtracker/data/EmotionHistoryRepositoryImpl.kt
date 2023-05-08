package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.local.LocalEmotionHistoryDataSource
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtrackerdb.DayToHappinessLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import java.time.ZonedDateTime

class EmotionHistoryRepositoryImpl(
    private val localDataSource: LocalEmotionHistoryDataSource
) : EmotionHistoryRepository {

    override suspend fun getDayToAverageHappinessLevel(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): List<DayToHappinessLevel> {
        return localDataSource.getDayToAverageHappinessLevel(startDate, endDate)
    }

    override fun getEmotionsHistoryByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): Flow<Result<List<EmotionHistory>>> {
        return flow {
            emit(Result.Loading())

            emit(
                Result.Success(
                    localDataSource.getEmotionsHistoryByDate(startDate, endDate)
                )
            )
        }
    }

    override fun getEmotionHistoryById(
        id: Long
    ): Flow<Result<EmotionHistory?>> {
        return flow {
            emit(Result.Loading())

            val emotionHistory = localDataSource.getEmotionHistoryById(id)
            emit(
                if (emotionHistory == null) {
                    Result.Error(RuntimeException("No items found"))
                } else {
                    Result.Success(emotionHistory)
                }
            )
        }
    }

    override suspend fun getEmotions(): List<Emotion> {
        return localDataSource.getEmotions()
    }

    override suspend fun getActivities(): List<Activity> {
        return localDataSource.getActivities()
    }

    override suspend fun insertOrUpdateEmotionHistory(
        id: Long?,
        date: ZonedDateTime,
        emotionId: Long,
        selectedActivityIds: List<Long>,
        note: String?
    ) {
        localDataSource.insertOrUpdateEmotionHistory(
            id = id,
            date = date,
            emotionId = emotionId,
            selectedActivityIds = selectedActivityIds,
            note = note
        )
    }

    override suspend fun deleteEmotionHistory(emotionHistory: EmotionHistory) {
        localDataSource.deleteEmotionHistory(emotionHistory)
    }
}