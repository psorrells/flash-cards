package com.pamela.flashcards.database.flashcards

import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

abstract class FlashCardsRepository {
    abstract suspend fun getAllCardsByDeckId(deckId: UUID): List<FlashCardDomain>

    abstract suspend fun getCardById(id: UUID): FlashCardDomain

    abstract suspend fun getNextDueCardByDeckId(deckId: UUID): FlashCardDomain?

    abstract suspend fun upsertCardToDeck(card: FlashCardDomain, deckId: UUID)

    abstract suspend fun upsertAllCardsToDeck(cards: List<FlashCardDomain>, deckId: UUID)

    abstract suspend fun deleteCardFromDeck(card: FlashCardDomain, deckId: UUID)
}

@Singleton
class FlashCardsRepositoryImpl @Inject constructor(
    private val flashCardsDao: FlashCardsDao
) : FlashCardsRepository() {
    override suspend fun getAllCardsByDeckId(deckId: UUID): List<FlashCardDomain> {
        return flashCardsDao.getAllCardsByDeckId(deckId.toString()).map { it.toDomain() }
    }

    override suspend fun getCardById(id: UUID): FlashCardDomain {
        return flashCardsDao.getCardById(id.toString()).toDomain()
    }

    override suspend fun getNextDueCardByDeckId(deckId: UUID): FlashCardDomain? {
        return flashCardsDao.getNextDueCardByDeckIdAndDueDate(deckId.toString())?.toDomain()
    }

    override suspend fun upsertCardToDeck(card: FlashCardDomain, deckId: UUID) {
        flashCardsDao.upsert(card.toEntity(deckId))
    }

    override suspend fun upsertAllCardsToDeck(cards: List<FlashCardDomain>, deckId: UUID) {
        flashCardsDao.upsertAll(*cards.map { it.toEntity(deckId) }.toTypedArray())
    }

    override suspend fun deleteCardFromDeck(card: FlashCardDomain, deckId: UUID) {
        flashCardsDao.delete(card.toEntity(deckId))
    }

}