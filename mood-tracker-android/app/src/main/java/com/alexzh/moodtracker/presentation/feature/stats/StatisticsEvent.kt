package com.alexzh.moodtracker.presentation.feature.stats

sealed class StatisticsEvent {
    object LoadPreviousWeek : StatisticsEvent()
    object LoadNextWeek: StatisticsEvent()
    object LoadCurrentWeek : StatisticsEvent()
}
