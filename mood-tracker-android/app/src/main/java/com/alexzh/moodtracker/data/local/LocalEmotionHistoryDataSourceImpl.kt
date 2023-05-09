package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.Database
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtrackerdb.ActivityEntity
import com.alexzh.moodtrackerdb.DayToHappinessLevel
import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class LocalEmotionHistoryDataSourceImpl(
    private val db: Database,
    private val dispatcher: CoroutineDispatcher
): LocalEmotionHistoryDataSource {
    private val emotionsQueries = db.emotionEntityQueries
    private val activitiesQueries = db.activityEntityQueries
    private val emotionHistoryQueries = db.emotionHistoryEntityQueries
    private val dayToAverageHappinessLevelQueries = db.dayToHappinessLevelQueries
    private val emotionHistoryToActivityQueries = db.emotionHistoryToActivityEntityQueries
    private val emotionHistoryWithActivitiesQueries = db.emotionHistoryWithActivitiesQueries

    override suspend fun getDayToAverageHappinessLevel(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): List<DayToHappinessLevel> {
        return withContext(dispatcher) {
            dayToAverageHappinessLevelQueries
                .getDayToHappinessLevel(startDate, endDate)
                .executeAsList()
        }
    }

    override suspend fun getEmotionsHistoryByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): List<EmotionHistory> {
        return withContext(dispatcher) {
            val localEmotionHistoryWithActivity = emotionHistoryWithActivitiesQueries
                .getEmotionHistoryWithActivitiesByDate(startDate, endDate)
                .executeAsList()
            val activities = activitiesQueries
                .getActivities()
                .executeAsList()

            localEmotionHistoryWithActivity.map { emotionHistoryWithActivity ->
                EmotionHistory(
                    id = emotionHistoryWithActivity.id,
                    date = emotionHistoryWithActivity.date,
                    emotion = Emotion(
                        id = emotionHistoryWithActivity.emotionId,
                        name = emotionHistoryWithActivity.emotionName,
                        happinessLevel = emotionHistoryWithActivity.emotionHappinessLevel,
                        icon = emotionHistoryWithActivity.emotionIcon
                    ),
                    activities = emotionHistoryWithActivity.activityIds.split(",")
                        .map { activityId ->
                            val activity = activities.first { it.id == activityId.toLong() }
                            Activity(
                                id = activity.id,
                                name = activity.name,
                                icon = activity.icon
                            )
                        },
                    note = emotionHistoryWithActivity.note
                )
            }
        }
    }

    override suspend fun getEmotionHistoryById(
        id: Long
    ): EmotionHistory? {
        return withContext(dispatcher) {
            val localEmotionHistoryWithActivity = emotionHistoryWithActivitiesQueries
                .getEmotionHistoryWithActivitiesById(id)
                .executeAsOneOrNull()
            val activities = activitiesQueries
                .getActivities()
                .executeAsList()

            localEmotionHistoryWithActivity?.let {
                mapToEmotionHistory(
                    localEmotionHistoryWithActivity,
                    activities
                )
            }
        }
    }

    override suspend fun getEmotions(): List<Emotion> {
        return withContext(dispatcher) {
            emotionsQueries.getEmotions().executeAsList().map { emotion ->
                Emotion(
                    id = emotion.id,
                    name = emotion.name,
                    happinessLevel = emotion.happinessLevel,
                    icon = emotion.icon
                )
            }
        }
    }

    override suspend fun getActivities(): List<Activity> {
        return withContext(dispatcher) {
            activitiesQueries.getActivities().executeAsList().map { activity ->
                Activity(
                    id = activity.id,
                    name = activity.name,
                    icon = activity.icon
                )
            }
        }
    }

    override suspend fun insertOrUpdateEmotionHistory(
        date: ZonedDateTime,
        emotionId: Long,
        selectedActivityIds: List<Long>,
        note: String?,
        id: Long?,
    ) {
        withContext(dispatcher) {
            val activityIds = if (id != null) {
                emotionHistoryToActivityQueries
                    .getActivitiesByEmotionHistory(id)
                    .executeAsList()
                    .map { it.activityId }
            } else emptyList()

            db.transaction {
                emotionHistoryQueries.insert(
                    date = date,
                    emotionId = emotionId,
                    note = note?.ifEmpty { null },
                    id = id
                )
                val newId = emotionHistoryQueries.getLastInsertedRowId().executeAsOneOrNull()
                if (newId != null) {
                    val activitiesToDelete =
                        activityIds.filter { !selectedActivityIds.contains(it) }
                    val activitiesToInsert =
                        selectedActivityIds.filter { !activityIds.contains(it) }

                    activitiesToDelete.forEach { activityId ->
                        emotionHistoryToActivityQueries.deleteByEmotionHistoryIdAndActivityId(
                            newId,
                            activityId
                        )
                    }

                    activitiesToInsert.forEach { activityId ->
                        emotionHistoryToActivityQueries.insert(
                            emotionHistoryId = newId,
                            activityId = activityId,
                            id = null
                        )
                    }
                } else {
                    rollback()
                }
            }
        }
    }

    override suspend fun deleteEmotionHistory(emotionHistory: EmotionHistory) {
        withContext(dispatcher) {
            db.transaction {
                val activitiesToDelete = emotionHistory.activities.map { it.id }
                activitiesToDelete.forEach {
                    emotionHistoryToActivityQueries.deleteByEmotionHistoryIdAndActivityId(emotionHistory.id, it)
                }

                emotionHistoryQueries.delete(emotionHistory.id)
            }
        }
    }

    private fun mapToEmotionHistory(
        emotionHistoryWithActivity: EmotionHistoryWithActivities,
        activities: List<ActivityEntity>
    ) = EmotionHistory(
        id = emotionHistoryWithActivity.id,
        date = emotionHistoryWithActivity.date,
        emotion = Emotion(
            id = emotionHistoryWithActivity.emotionId,
            name = emotionHistoryWithActivity.emotionName,
            happinessLevel = emotionHistoryWithActivity.emotionHappinessLevel,
            icon = emotionHistoryWithActivity.emotionIcon
        ),
        activities = emotionHistoryWithActivity.activityIds.split(",").map { activityId ->
            val activity = activities.first { it.id == activityId.toLong() }
            Activity(
                id = activity.id,
                name = activity.name,
                icon = activity.icon
            )
        },
        note = emotionHistoryWithActivity.note
    )
}