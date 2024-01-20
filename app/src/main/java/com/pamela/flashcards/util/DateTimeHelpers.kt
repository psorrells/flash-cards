package com.pamela.flashcards.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val ONE_MINUTE_IN_MILLIS = 60000L
const val ONE_HOUR_IN_MILLIS = 3600000L
const val ONE_DAY_IN_MILLIS = 86400000L
val defaultDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/mm/yy")
val defaultTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")


fun Instant.getFormattedDate(): String {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault()).format(defaultDateFormat)
}

fun Instant.getFormattedTime(): String {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault()).format(defaultTimeFormat)
}

fun getMinutesInMillis(minutes: Int): Long {
    return minutes * ONE_MINUTE_IN_MILLIS
}

fun getHoursInMillis(hours: Int): Long {
    return hours * ONE_HOUR_IN_MILLIS
}

fun getDaysInMillis(days: Int): Long {
    return days * ONE_DAY_IN_MILLIS
}