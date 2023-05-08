package com.alexzh.moodtracker.presentation.core.date

import java.time.LocalDate

class DateProviderImpl(
    private val currentDate: LocalDate
): DateProvider {

    override fun getCurrentDate(): LocalDate {
        return currentDate
    }
}