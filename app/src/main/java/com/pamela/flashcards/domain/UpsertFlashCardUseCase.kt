package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.InvalidFlashCardDeckIdError
import com.pamela.flashcards.util.getCancellableResult
import java.util.UUID
import javax.inject.Inject

class UpsertFlashCardUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository,
    private val getFlashCardDeckByIdUseCase: GetFlashCardDeckByIdUseCase
) {
    suspend operator fun invoke(card: FlashCardDomain): Result<Unit> {
        return getCancellableResult {
            if (getFlashCardDeckByIdUseCase(card.deckId).isFailure) throw InvalidFlashCardDeckIdError(card.deckId)
            flashCardsRepository.upsertCard(card)
            Result.success(Unit)
        }
    }
}