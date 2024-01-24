package com.pamela.flashcards.domain.flashcard

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class GetNextDueCardUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(): Result<FlashCardDomain> {
        return getCancellableResult {
            val result = flashCardsRepository.getNextDueCard()
            if (result == null) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        }
    }
}