package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject

class GetNextDueCardByDeckIdUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(deckId: UUID): Result<FlashCardDomain> {
        return try {
            val result = flashCardsRepository.getNextDueCardByDeckId(deckId)
            if (result == null) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}