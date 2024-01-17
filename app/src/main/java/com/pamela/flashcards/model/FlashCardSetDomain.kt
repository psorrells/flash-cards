package com.pamela.flashcards.model

import com.pamela.flashcards.database.flashcardsets.FlashCardSetEntity
import java.time.Instant
import java.util.UUID

data class FlashCardSetDomain(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val size: Int = 0,
    val totalDue: Int = 0,
    val createdAt: Instant = Instant.now(),
    val lastStudiedAt: Instant? = null
) {
    fun toEntity(): FlashCardSetEntity {
        return FlashCardSetEntity(
            id = id.toString(),
            name = name,
            createdAt = createdAt.toEpochMilli()
        )
    }
}

