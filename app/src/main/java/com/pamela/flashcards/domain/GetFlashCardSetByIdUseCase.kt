package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.model.FlashCardSetDomain
import java.util.UUID
import javax.inject.Inject

class GetFlashCardSetByIdUseCase @Inject constructor(
    private val flashCardSetsRepository: FlashCardSetsRepository
) {
    suspend operator fun invoke(setId: UUID): Result<FlashCardSetDomain> {
        return try {
            val result = flashCardSetsRepository.getSetById(setId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}