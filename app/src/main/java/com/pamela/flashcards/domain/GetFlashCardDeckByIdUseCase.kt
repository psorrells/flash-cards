package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.FlashCardDeckDomain
import java.util.UUID
import javax.inject.Inject

class GetFlashCardDeckByIdUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(deckId: UUID): Result<FlashCardDeckDomain> {
        return try {
            val result = flashCardDecksRepository.getDeckById(deckId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}