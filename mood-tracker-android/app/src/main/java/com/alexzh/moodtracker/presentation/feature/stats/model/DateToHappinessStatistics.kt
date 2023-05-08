package com.alexzh.moodtracker.presentation.feature.stats.model

import java.time.LocalDate

data class DateToHappinessStatistics(
    val datePeriod: String = "",
    val items: List<Pair<LocalDate, Float>> = emptyList()
)