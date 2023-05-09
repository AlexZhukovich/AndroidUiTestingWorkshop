package com.alexzh.moodtracker.testdata

import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

object EmotionHistoryTestData {

    val EMOTION_HISTORY_ITEM: (LocalDate) -> EmotionHistory = { date ->
        EmotionHistory(
            id = 1,
            date = ZonedDateTime.of(date, LocalTime.of(8, 15), DATE_TIME_ZONE_UTC),
            emotion = Emotion(
                id = 1,
                name = "excited",
                happinessLevel = 5,
                icon = "emotion-excited"
            ),
            activities = listOf(
                Activity(
                    id = 1,
                    name = "work",
                    icon = "activity-work"
                )
            ),
            note = "Test note"
        )
    }

    val EMOTION_HISTORY_ITEMS: (LocalDate) -> List<EmotionHistory> = { date ->
        listOf(
            EmotionHistory(
                id = 1,
                date = ZonedDateTime.of(date, LocalTime.of(8, 15), DATE_TIME_ZONE_UTC),
                emotion = Emotion(
                    id = 1,
                    name = "excited",
                    happinessLevel = 5,
                    icon = "emotion-excited"
                ),
                activities = listOf(
                    Activity(
                        id = 1,
                        name = "work",
                        icon = "activity-work"
                    )
                ),
                note = "Test note 1"
            ),
            EmotionHistory(
                id = 1,
                date = ZonedDateTime.of(date, LocalTime.of(21, 30), DATE_TIME_ZONE_UTC),
                emotion = Emotion(
                    id = 1,
                    name = "neutral",
                    happinessLevel = 3,
                    icon = "emotion-neutral"
                ),
                activities = listOf(
                    Activity(
                        id = 1,
                        name = "work",
                        icon = "activity-work"
                    ),
                    Activity(
                        id = 2,
                        name = "shopping",
                        icon = "activity-shopping"
                    )
                ),
                note = "Test note 2"
            )
        )
    }
}