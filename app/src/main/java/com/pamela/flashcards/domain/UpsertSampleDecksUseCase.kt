package com.pamela.flashcards.domain

import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.model.awsDeck
import com.pamela.flashcards.model.awsFlashCards
import com.pamela.flashcards.model.dataDeck
import com.pamela.flashcards.model.dataFlashCards
import com.pamela.flashcards.model.kubernetesDeck
import com.pamela.flashcards.model.kubernetesFlashCards
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class UpsertSampleDecksUseCase @Inject constructor(
    private val flashCardsRepository: FlashCardsRepository,
    private val flashCardDecksRepository: FlashCardDecksRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return getCancellableResult {
            flashCardDecksRepository.upsertDeck(kubernetesDeck)
            flashCardsRepository.upsertAllCards(kubernetesFlashCards.map {  it.copy(deckId = kubernetesDeck.id) })
            flashCardDecksRepository.upsertDeck(awsDeck)
            flashCardsRepository.upsertAllCards(awsFlashCards.map {  it.copy(deckId = awsDeck.id) })
            flashCardDecksRepository.upsertDeck(dataDeck)
            flashCardsRepository.upsertAllCards(dataFlashCards.map {  it.copy(deckId = dataDeck.id) })
            Result.success(Unit)
        }
    }
}