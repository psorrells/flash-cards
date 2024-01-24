package com.pamela.flashcards.domain.deck

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class GetAllFlashCardDecksUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardDeckDomain>> {
        return getCancellableResult {
            val result = flashCardDecksRepository.getAllDecks()

            if (result.isEmpty()) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        }
    }
}