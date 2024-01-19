package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject

class UpsertFlashCardToSetUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(card: FlashCardDomain, setId: UUID): Result<Unit> {
        return try {
            flashCardsRepository.upsertCardToSet(card, setId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}