package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.model.FlashCardSetDomain
import javax.inject.Inject

class InsertFlashCardSetUseCase @Inject constructor(
    private val flashCardSetsRepository: FlashCardSetsRepository
) {
    suspend operator fun invoke(set: FlashCardSetDomain): Result<Unit> {
        return try {
            flashCardSetsRepository.insertSet(set)
                Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}