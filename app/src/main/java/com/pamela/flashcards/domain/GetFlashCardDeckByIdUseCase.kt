package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.util.getCancellableResult
import java.util.UUID
import javax.inject.Inject

class GetFlashCardDeckByIdUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(deckId: UUID): Result<FlashCardDeckDomain> {
        return getCancellableResult {
            val result = flashCardDecksRepository.getDeckById(deckId)
            Result.success(result)
        }
    }
}