package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.FlashCardDeckDomain
import javax.inject.Inject

class DeleteFlashCardDeckUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(deck: FlashCardDeckDomain): Result<Unit> {
        return try {
            flashCardDecksRepository.deleteDeck(deck)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}