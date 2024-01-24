package com.pamela.flashcards.domain

import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.model.FlashCardDomain
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

class UpdateFlashCardStatsUseCase @Inject constructor(
    private val upsertFlashCard: UpsertFlashCardUseCase,
    private val getNewIntervalByDifficulty: GetNewIntervalByDifficultyUseCase
) {
    suspend operator fun invoke(flashCard: FlashCardDomain, difficulty: Difficulty): Result<Unit> {
        val now = Instant.now()
        val newIntervalInMillis: Long = getNewIntervalByDifficulty(flashCard, difficulty)
        val newDueDate: Instant = now.plusMillis(newIntervalInMillis)

        val updatedFlashCard = flashCard.copy(
            lastStudiedAt = now,
            lastIntervalInMillis = newIntervalInMillis,
            nextDueAt = newDueDate
        )

        return upsertFlashCard(updatedFlashCard)
    }
}