package com.alexzh.moodtracker.presentation.feature.today

import java.time.LocalDate

sealed class TodayEvent {
    data class OnDateChange(val date: LocalDate): TodayEvent()
}
