package com.pamela.flashcards.util

import java.util.UUID

fun getUuidOrNull(string: String?): UUID? {
    return try {
        UUID.fromString(string)
    } catch (e: Exception) {
        null
    }
}