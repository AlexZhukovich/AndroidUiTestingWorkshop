package com.alexzh.moodtracker.presentation.core.date

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DefaultTimeFormatter : TimeFormatter {

    override fun format(date: LocalDateTime): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return formatter.format(date)
    }
}