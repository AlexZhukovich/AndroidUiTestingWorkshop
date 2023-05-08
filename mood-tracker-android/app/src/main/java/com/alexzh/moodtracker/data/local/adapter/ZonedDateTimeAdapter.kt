package com.alexzh.moodtracker.data.local.adapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

val DATE_TIME_ZONE_UTC = ZoneId.of("UTC")

val zonedDateTimeAdapter = object : ColumnAdapter<ZonedDateTime, Long> {
    override fun decode(databaseValue: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(databaseValue), DATE_TIME_ZONE_UTC)
    }

    override fun encode(value: ZonedDateTime): Long {
        return value.toEpochSecond()
    }
}