package com.alexzh.moodtracker.presentation.feature.addmood

import com.alexzh.moodtracker.presentation.core.SelectableActivityItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem
import java.time.LocalDate
import java.time.LocalTime

enum class AddMoodScreenLoading {
    LOADING,
    SAVING,
    DELETING,
    NONE
}

enum class AddMoodScreenCompletedOperation {
    SAVED,
    DELETED,
    NONE
}

data class AddMoodScreenState(
    val loading: AddMoodScreenLoading = AddMoodScreenLoading.NONE,
    val completedOperation: AddMoodScreenCompletedOperation = AddMoodScreenCompletedOperation.NONE,
    val emotions: List<SelectableEmotionItem> = emptyList(),
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val activities: List<SelectableActivityItem> = emptyList(),
    val note: String = "",
    val is24hFormat: Boolean = true
)