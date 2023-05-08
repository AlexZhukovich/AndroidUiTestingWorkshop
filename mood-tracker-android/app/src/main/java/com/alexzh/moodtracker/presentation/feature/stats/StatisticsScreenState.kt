package com.alexzh.moodtracker.presentation.feature.stats

import com.alexzh.moodtracker.presentation.feature.stats.model.DateToHappinessStatistics

data class StatisticsScreenState(
    val loading: Boolean = false,
    val dateToHappinessData: DateToHappinessStatistics = DateToHappinessStatistics()
)
