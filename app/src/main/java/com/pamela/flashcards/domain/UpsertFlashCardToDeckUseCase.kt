package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.InvalidFlashCardDeckIdError
import java.util.UUID
import javax.inject.Inject

class UpsertFlashCardToDeckUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository,
    private val getFlashCardDeckByIdUseCase: GetFlashCardDeckByIdUseCase
) {
    suspend operator fun invoke(card: FlashCardDomain, deckId: UUID): Result<Unit> {
        return try {
            if (getFlashCardDeckByIdUseCase(deckId).isFailure) throw InvalidFlashCardDeckIdError(deckId)
            flashCardsRepository.upsertCardToDeck(card, deckId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}