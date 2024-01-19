package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject

class GetFlashCardByIdUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(setId: UUID): Result<FlashCardDomain> {
        return try {
            val result = flashCardsRepository.getCardById(setId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}