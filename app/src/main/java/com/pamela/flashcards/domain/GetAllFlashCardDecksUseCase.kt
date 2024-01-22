package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDeckDomain
import javax.inject.Inject

class GetAllFlashCardDecksUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardDeckDomain>> {
        return try {
            val result = flashCardDecksRepository.getAllDecks()

            if (result.isEmpty()) {
                throw EmptyResultError()
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}