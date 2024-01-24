package com.pamela.flashcards.domain.flashcard

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.util.getCancellableResult
import java.util.UUID
import javax.inject.Inject

class GetFlashCardsByDeckIdUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(deckId: UUID): Result<List<FlashCardDomain>> {
        return getCancellableResult {
            val result = flashCardsRepository.getAllCardsByDeckId(deckId)
            if (result.isEmpty()) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        }
    }
}