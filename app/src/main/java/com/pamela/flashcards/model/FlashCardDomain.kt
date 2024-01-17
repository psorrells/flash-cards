package com.pamela.flashcards.model

import com.pamela.flashcards.repository.flashcards.FlashCardEntity
import java.time.Instant
import java.util.UUID

data class FlashCardDomain(
    val id: UUID = UUID.randomUUID(),
    val front: String = "",
    val back: String = "",
    val difficulty: Int = 0,
    val createdAt: Instant = Instant.now(),
    val lastStudiedAt: Instant? = null,
    val lastIntervalInMillis: Int = 0,
    val nextDueAt: Instant = Instant.now()
) {
    fun toEntity(setId: UUID): FlashCardEntity {
        return FlashCardEntity(
            id = id.toString(),
            setId = setId.toString(),
            front = front,
            back = back,
            difficulty = difficulty,
            createdAt = createdAt.toEpochMilli(),
            lastStudiedAt = lastStudiedAt?.toEpochMilli(),
            lastIntervalInMillis = lastIntervalInMillis,
            nextDueAt = nextDueAt.toEpochMilli()

        )
    }
}