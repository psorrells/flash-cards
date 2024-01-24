package com.pamela.flashcards.domain

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.util.getCancellableResult
import java.util.UUID
import javax.inject.Inject

class GetFlashCardByIdUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository
) {
    suspend operator fun invoke(id: UUID): Result<FlashCardDomain> {
        return getCancellableResult {
            val result = flashCardsRepository.getCardById(id)
            Result.success(result)
        }
    }
}