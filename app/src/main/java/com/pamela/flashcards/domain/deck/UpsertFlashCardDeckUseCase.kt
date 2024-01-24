package com.pamela.flashcards.domain.deck

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class UpsertFlashCardDeckUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(deck: FlashCardDeckDomain): Result<Unit> {
        return getCancellableResult {
            flashCardDecksRepository.upsertDeck(deck)
            Result.success(Unit)
        }
    }
}