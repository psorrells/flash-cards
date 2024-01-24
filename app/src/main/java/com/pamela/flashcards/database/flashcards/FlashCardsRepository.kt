package com.pamela.flashcards.database.flashcards

import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

abstract class FlashCardsRepository {
    abstract suspend fun getAllCardsByDeckId(deckId: UUID): List<FlashCardDomain>

    abstract suspend fun getCardById(id: UUID): FlashCardDomain

    abstract suspend fun getNextDueCardByDeckId(deckId: UUID): FlashCardDomain?

    abstract suspend fun getNextDueCard(): FlashCardDomain?

    abstract suspend fun upsertCard(card: FlashCardDomain)

    abstract suspend fun upsertAllCards(cards: List<FlashCardDomain>)

    abstract suspend fun deleteCard(card: FlashCardDomain)
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

    override suspend fun getNextDueCard(): FlashCardDomain? {
        return flashCardsDao.getNextDueCardByDueDate()?.toDomain()
    }

    override suspend fun upsertCard(card: FlashCardDomain) {
        flashCardsDao.upsert(card.toEntity())
    }

    override suspend fun upsertAllCards(cards: List<FlashCardDomain>) {
        flashCardsDao.upsertAll(*cards.map { it.toEntity() }.toTypedArray())
    }

    override suspend fun deleteCard(card: FlashCardDomain) {
        flashCardsDao.deleteCardById(card.id.toString())
    }

}