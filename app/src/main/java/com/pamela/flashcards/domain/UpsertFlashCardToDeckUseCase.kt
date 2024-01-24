package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.InvalidFlashCardDeckIdError
import java.util.UUID
import javax.inject.Inject

class UpsertFlashCardToDeckUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository,
    private val getFlashCardDeckByIdUseCase: GetFlashCardDeckByIdUseCase
) {
    suspend operator fun invoke(card: FlashCardDomain, deckId: UUID?): Result<Unit> {
        val id = deckId ?: card.deckId
        return try {
            if (getFlashCardDeckByIdUseCase(id).isFailure) throw InvalidFlashCardDeckIdError(id)
            flashCardsRepository.upsertCard(card.copy(deckId = id))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}