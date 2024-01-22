package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import javax.inject.Inject

class GetAllFlashCardDecksNameIdUseCase @Inject constructor(
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardDeckNameIdDomain>> {
        return try {
            val result = flashCardDecksRepository.getAllDecksNameIds()

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