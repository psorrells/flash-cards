package com.pamela.flashcards.domain.flashcard

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class DeleteFlashCardUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(card: FlashCardDomain): Result<Unit> {
        return getCancellableResult {
            flashCardsRepository.deleteCard(card)
            Result.success(Unit)
        }
    }
}