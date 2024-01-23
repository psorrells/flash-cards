package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import javax.inject.Inject

class DeleteFlashCardUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(card: FlashCardDomain): Result<Unit> {
        return try {
            flashCardsRepository.deleteCard(card)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}