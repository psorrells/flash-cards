package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.awsDeck
import com.pamela.flashcards.model.awsFlashCards
import com.pamela.flashcards.model.dataDeck
import com.pamela.flashcards.model.dataFlashCards
import com.pamela.flashcards.model.kubernetesDeck
import com.pamela.flashcards.model.kubernetesFlashCards
import javax.inject.Inject

class UpsertSampleDecksUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository,
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            flashCardDecksRepository.upsertDeck(kubernetesDeck)
            flashCardsRepository.upsertAllCardsToDeck(kubernetesFlashCards, kubernetesDeck.id)
            flashCardDecksRepository.upsertDeck(awsDeck)
            flashCardsRepository.upsertAllCardsToDeck(awsFlashCards, awsDeck.id)
            flashCardDecksRepository.upsertDeck(dataDeck)
            flashCardsRepository.upsertAllCardsToDeck(dataFlashCards, dataDeck.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}