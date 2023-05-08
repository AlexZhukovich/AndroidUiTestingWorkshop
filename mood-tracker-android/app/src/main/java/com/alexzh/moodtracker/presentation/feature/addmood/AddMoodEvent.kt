package com.alexzh.moodtracker.presentation.feature.addmood

import java.time.LocalDate
import java.time.LocalTime

sealed class AddMoodEvent {
    data class Load(val emotionHistoryId: Long): AddMoodEvent()
    data class OnEmotionChange(val emotionId: Long) : AddMoodEvent()
    data class OnActivityChange(val activityId: Long) : AddMoodEvent()
    data class OnNoteChange(val note: String): AddMoodEvent()
    data class OnDateChange(val date: LocalDate): AddMoodEvent()
    data class OnTimeChange(val time: LocalTime): AddMoodEvent()
    object Delete: AddMoodEvent()
    object Save : AddMoodEvent()
}
