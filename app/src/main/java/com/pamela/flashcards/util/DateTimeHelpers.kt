package com.pamela.flashcards.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val defaultDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/mm/yy")
val defaultTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")


fun Instant.getFormattedDate(): String {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault()).format(defaultDateFormat)
}

fun Instant.getFormattedTime(): String {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault()).format(defaultTimeFormat)
}