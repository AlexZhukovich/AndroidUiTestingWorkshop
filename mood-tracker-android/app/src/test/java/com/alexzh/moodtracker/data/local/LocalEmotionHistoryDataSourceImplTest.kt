package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.Database
import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.data.local.adapter.zonedDateTimeAdapter
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtrackerdb.DayToHappinessLevel
import com.alexzh.moodtrackerdb.EmotionHistoryEntity
import com.alexzh.moodtrackerdb.EmotionHistoryToActivityEntity
import com.google.common.truth.Truth.assertThat
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.*

@ExperimentalCoroutinesApi
class LocalEmotionHistoryDataSourceImplTest {
    companion object {
        val PREDEFINED_EMOTIONS = listOf(
            Emotion(id = 1L, name = "angry", happinessLevel = 1, icon = "emotion-angry"),
            Emotion(id = 2L, name = "confused", happinessLevel = 2, icon = "emotion-confused"),
            Emotion(id = 3L, name = "neutral", happinessLevel = 3, icon = "emotion-neutral"),
            Emotion(id = 4L, name = "happy", happinessLevel = 4, icon = "emotion-happy"),
            Emotion(id = 5L, name = "excited", happinessLevel = 5, icon = "emotion-excited")
        )
        val PREDEFINED_ACTIVITIES = listOf(
            Activity(id = 1L, name = "sport", icon = "activity-sport"),
            Activity(id = 2L, name = "work", icon = "activity-work"),
            Activity(id = 3L, name = "gardening", icon = "activity-gardening"),
            Activity(id = 4L, name = "relax", icon = "activity-relax"),
            Activity(id = 5L, name = "reading", icon = "activity-reading"),
            Activity(id = 6L, name = "gaming", icon = "activity-gaming"),
            Activity(id = 7L, name = "shopping", icon = "activity-shopping"),
            Activity(id = 8L, name = "traveling", icon = "activity-traveling"),
            Activity(id = 9L, name = "friends", icon = "activity-friends"),
            Activity(id = 10L, name = "family", icon = "activity-family"),
            Activity(id = 11L, name = "walking", icon = "activity-walking"),
            Activity(id = 12L, name = "cleaning", icon = "activity-cleaning"),
            Activity(id = 13L, name = "cooking", icon = "activity-cooking")
        )
    }

    private val database = Database(
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also(Database.Schema::create),
        emotionHistoryEntityAdapter = EmotionHistoryEntity.Adapter(dateAdapter = zonedDateTimeAdapter)
    )
    private val dispatcher = Dispatchers.Unconfined
    private val dataSource = LocalEmotionHistoryDataSourceImpl(
        database,
        dispatcher
    )

    @Test
    fun `getDayToAverageHappinessLevel returns list of day to happiness level when data is available for specific dates`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0, 0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59, 59), DATE_TIME_ZONE_UTC)
        val insertHistoryDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,56), DATE_TIME_ZONE_UTC)
        val expectedData = listOf(
            DayToHappinessLevel(insertHistoryDate, 1),
            DayToHappinessLevel(insertHistoryDate, 2),
            DayToHappinessLevel(insertHistoryDate, 3),
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = insertHistoryDate,
            emotionId = 1L,
            selectedActivityIds = listOf(1L),
            note = null
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = insertHistoryDate,
            emotionId = 2L,
            selectedActivityIds = listOf(1L),
            note = null
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = insertHistoryDate,
            emotionId = 3L,
            selectedActivityIds = listOf(1L),
            note = null
        )

        assertThat(dataSource.getDayToAverageHappinessLevel(startDate, endDate))
            .isEqualTo(expectedData)
    }

    @Test
    fun `getDayToAverageHappinessLevel returns empty list of day to happiness level when no data is available for specific dates`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0, 0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59, 59), DATE_TIME_ZONE_UTC)

        assertThat(dataSource.getDayToAverageHappinessLevel(startDate, endDate))
            .isEqualTo(emptyList<DayToHappinessLevel>())
    }

    @Test
    fun `getEmotionsHistoryByDate returns list of emotion history when data are available for specific date`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59), DATE_TIME_ZONE_UTC)
        val expectedEmotionHistory = EmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotion = PREDEFINED_EMOTIONS[3],
            activities = PREDEFINED_ACTIVITIES.filter { it.id == 1L },
            note = UUID.randomUUID().toString(),
            id = 2L
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = ZonedDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotionId = 1L,
            selectedActivityIds = listOf(1L),
            note = null
        )
        dataSource.insertOrUpdateEmotionHistory(
            date = expectedEmotionHistory.date,
            emotionId = expectedEmotionHistory.emotion.id,
            selectedActivityIds = expectedEmotionHistory.activities.map { it.id },
            note = expectedEmotionHistory.note
        )
        dataSource.insertOrUpdateEmotionHistory(
            date = ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotionId = 1L,
            selectedActivityIds = listOf(1L),
            note = null,
        )
        assertThat(dataSource.getEmotionsHistoryByDate(startDate, endDate))
            .isEqualTo(listOf(expectedEmotionHistory))
    }

    @Test
    fun `getEmotionsHistoryByDate returns empty list of emotion history when no data for specific date`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59), DATE_TIME_ZONE_UTC)

        dataSource.insertOrUpdateEmotionHistory(
            date = ZonedDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotionId = 1L,
            selectedActivityIds = listOf(1L),
            note = null,
            id = null
        )

        val result = dataSource.getEmotionsHistoryByDate(startDate, endDate)
        assertThat(result).isEmpty()
    }

    @Test
    fun `getEmotionHistoryById returns the object when object exists in database`() = runTest {
        val expectedEmotionHistory = EmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotion = PREDEFINED_EMOTIONS[4],
            activities = PREDEFINED_ACTIVITIES.filter { it.id == 1L || it.id == 2L || it.id == 3L},
            note = UUID.randomUUID().toString(),
            id = 42L
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = expectedEmotionHistory.date,
            emotionId = expectedEmotionHistory.emotion.id,
            selectedActivityIds = expectedEmotionHistory.activities.map { it.id },
            note = expectedEmotionHistory.note,
            id = expectedEmotionHistory.id
        )

        assertThat(dataSource.getEmotionHistoryById(expectedEmotionHistory.id))
            .isEqualTo(expectedEmotionHistory)
    }

    @Test
    fun `getEmotionHistoryById returns null when no objects available for specific id`() = runTest {
        assertThat(dataSource.getEmotionHistoryById(42L))
            .isNull()
    }

    @Test
    fun `getEmotions returns predefined list of emotions`() = runTest {
        assertThat(dataSource.getEmotions())
            .isEqualTo(PREDEFINED_EMOTIONS)
    }

    @Test
    fun `getEmotions returns empty list when all predefined emotions removed`() = runTest {
        database.emotionEntityQueries.apply {
            (1L..5L).forEach { id ->
                delete(id)
            }
        }
        assertThat(dataSource.getEmotions())
            .isEmpty()
    }

    @Test
    fun `getActivities returns predefined list of activities`() = runTest {
        assertThat(dataSource.getActivities())
            .isEqualTo(PREDEFINED_ACTIVITIES)
    }

    @Test
    fun `getActivities returns empty list when all predefined activities removed`() = runTest {
        database.activityEntityQueries.apply {
            (1L..13L).forEach { id ->
                delete(id)
            }
        }
        assertThat(dataSource.getActivities())
            .isEmpty()
    }

    @Test
    fun `insertOrUpdateEmotionHistory adds emotionHistory object to database when id is null`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59), DATE_TIME_ZONE_UTC)
        val expectedEmotionHistory = EmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(7,36), DATE_TIME_ZONE_UTC),
            emotion = PREDEFINED_EMOTIONS[3],
            activities = PREDEFINED_ACTIVITIES.filter { it.id == 1L || it.id == 5L || it.id == 9L},
            note = UUID.randomUUID().toString(),
            id = 42L
        )
        val expectedEmotionHistoryToActivity = listOf(
            EmotionHistoryToActivityEntity(id = 1L, emotionHistoryId = expectedEmotionHistory.id, activityId = 1L),
            EmotionHistoryToActivityEntity(id = 2L, emotionHistoryId = expectedEmotionHistory.id, activityId = 5L),
            EmotionHistoryToActivityEntity(id = 3L, emotionHistoryId = expectedEmotionHistory.id, activityId = 9L)
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = expectedEmotionHistory.date,
            emotionId = expectedEmotionHistory.emotion.id,
            selectedActivityIds = expectedEmotionHistory.activities.map { it.id },
            note = expectedEmotionHistory.note,
            id = expectedEmotionHistory.id
        )

        val emotionHistoryToActivity = database.emotionHistoryToActivityEntityQueries
            .getActivitiesByEmotionHistory(expectedEmotionHistory.id)
            .executeAsList()
        assertThat(dataSource.getEmotionsHistoryByDate(startDate, endDate))
            .isEqualTo(listOf(expectedEmotionHistory))
        assertThat(emotionHistoryToActivity)
            .isEqualTo(expectedEmotionHistoryToActivity)
    }

    @Test
    fun `insertOrUpdateEmotionHistory updated an emotionHistory object to database when object is already exist`() = runTest {
        val startDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(0,0), DATE_TIME_ZONE_UTC)
        val endDate = ZonedDateTime.of(LocalDate.now(), LocalTime.of(23,59), DATE_TIME_ZONE_UTC)
        val expectedEmotionHistory = EmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(11,59), DATE_TIME_ZONE_UTC),
            emotion = PREDEFINED_EMOTIONS.last(),
            activities = PREDEFINED_ACTIVITIES.filter { it.id == 3L || it.id == 4L || it.id == 5L},
            note = UUID.randomUUID().toString(),
            id = 42L
        )
        val expectedEmotionHistoryToActivity = listOf(
            EmotionHistoryToActivityEntity(id = 3L, emotionHistoryId = expectedEmotionHistory.id, activityId = 3L),
            EmotionHistoryToActivityEntity(id = 4L, emotionHistoryId = expectedEmotionHistory.id, activityId = 4L),
            EmotionHistoryToActivityEntity(id = 5L, emotionHistoryId = expectedEmotionHistory.id, activityId = 5L)
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotionId = 1L,
            selectedActivityIds = listOf(1L, 2L, 3L),
            note = UUID.randomUUID().toString(),
            id = expectedEmotionHistory.id
        )

        dataSource.insertOrUpdateEmotionHistory(
            date = expectedEmotionHistory.date,
            emotionId = expectedEmotionHistory.emotion.id,
            selectedActivityIds = expectedEmotionHistory.activities.map { it.id },
            note = expectedEmotionHistory.note,
            id = expectedEmotionHistory.id
        )

        val emotionHistoryToActivity = database.emotionHistoryToActivityEntityQueries
            .getActivitiesByEmotionHistory(expectedEmotionHistory.id)
            .executeAsList()
        assertThat(dataSource.getEmotionsHistoryByDate(startDate, endDate))
            .isEqualTo(listOf(expectedEmotionHistory))
        assertThat(emotionHistoryToActivity)
            .isEqualTo(expectedEmotionHistoryToActivity)
    }

    @Test
    fun `deleteEmotionHistory removed emotionHistory object from database when object is exist in database`() = runTest {
        val id = 42L
        dataSource.insertOrUpdateEmotionHistory(
            date = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,56), DATE_TIME_ZONE_UTC),
            emotionId = 3L,
            selectedActivityIds = listOf(1L, 2L, 3L),
            note = UUID.randomUUID().toString(),
            id = id
        )
        val emotionHistory = requireNotNull(dataSource.getEmotionHistoryById(id))
        dataSource.deleteEmotionHistory(emotionHistory)

        val emotionHistoryToActivity = database.emotionHistoryToActivityEntityQueries
            .getActivitiesByEmotionHistory(id)
            .executeAsList()
        assertThat(dataSource.getEmotionHistoryById(id))
            .isNull()
        assertThat(emotionHistoryToActivity)
            .isEmpty()
    }
}