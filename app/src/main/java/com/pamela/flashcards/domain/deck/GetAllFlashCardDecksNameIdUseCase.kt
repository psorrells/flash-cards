package com.pamela.flashcards.domain.deck

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class GetAllFlashCardDecksNameIdUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardDeckNameIdDomain>> {
        return getCancellableResult {
            val result = flashCardDecksRepository.getAllDecksNameIds()

            if (result.isEmpty()) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        }
    }
}