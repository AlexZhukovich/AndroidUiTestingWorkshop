package com.alexzh.moodtracker.presentation.core.date

import java.time.LocalDateTime

interface TimeFormatter {

    fun format(date: LocalDateTime): String
}