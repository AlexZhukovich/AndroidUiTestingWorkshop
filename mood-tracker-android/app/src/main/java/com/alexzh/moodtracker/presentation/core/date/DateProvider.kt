package com.alexzh.moodtracker.presentation.core.date

import java.time.LocalDate

interface DateProvider {

    fun getCurrentDate(): LocalDate
}