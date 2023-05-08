package com.alexzh.moodtracker.presentation.feature.today

import com.alexzh.moodtracker.presentation.feature.today.model.EmotionHistoryItem
import java.time.LocalDate

data class TodayScreenState(
    val isLoading: Boolean = false,
    val is24hFormat: Boolean = true,
    val date: LocalDate = LocalDate.now(),
    val items: List<EmotionHistoryItem> = emptyList(),
)
