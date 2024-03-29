package com.pamela.flashcards.model

import com.pamela.flashcards.database.flashcards.FlashCardEntity
import java.time.Instant
import java.util.UUID

data class FlashCardDomain(
    val id: UUID = UUID.randomUUID(),
    val deckId: UUID = UUID.randomUUID(),
    val front: String = "",
    val back: String = "",
    val createdAt: Instant = Instant.now(),
    val lastStudiedAt: Instant? = null,
    val lastIntervalInMillis: Long = 0L,
    val nextDueAt: Instant = Instant.now()
) {
    fun toEntity(): FlashCardEntity {
        return FlashCardEntity(
            id = id.toString(),
            deckId = deckId.toString(),
            front = front,
            back = back,
            createdAt = createdAt.toEpochMilli(),
            lastStudiedAt = lastStudiedAt?.toEpochMilli(),
            lastIntervalInMillis = lastIntervalInMillis,
            nextDueAt = nextDueAt.toEpochMilli()
        )
    }
}