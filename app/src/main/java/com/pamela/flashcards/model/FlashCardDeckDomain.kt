package com.pamela.flashcards.model

import com.pamela.flashcards.database.decks.FlashCardDeckEntity
import java.time.Instant
import java.util.UUID

data class FlashCardDeckDomain(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val size: Int = 0,
    val totalDue: Int = 0,
    val createdAt: Instant = Instant.now(),
    val lastStudiedAt: Instant? = null
) {
    fun toEntity(): FlashCardDeckEntity {
        return FlashCardDeckEntity(
            id = id.toString(),
            name = name,
            createdAt = createdAt.toEpochMilli()
        )
    }
}

data class FlashCardDeckNameIdDomain(
    val id: UUID = UUID.randomUUID(),
    val name: String = ""
)

