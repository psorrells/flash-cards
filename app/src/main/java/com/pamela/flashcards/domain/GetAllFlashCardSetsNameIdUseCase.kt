package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FlashCardSetNameIdDomain
import javax.inject.Inject

class GetAllFlashCardSetsNameIdUseCase @Inject constructor(
    private val flashCardSetsRepository: FlashCardSetsRepository
) {
    suspend operator fun invoke(): Result<List<FlashCardSetNameIdDomain>> {
        return try {
            val result = flashCardSetsRepository.getAllSetsNameIds()

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