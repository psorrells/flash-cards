package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardSetDomain
import javax.inject.Inject

class GetAllFlashCardSetsUseCase @Inject constructor(
    private val flashCardSetsRepository: FlashCardSetsRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardSetDomain>> {
        return try {
            val result = flashCardSetsRepository.getAllSets()

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