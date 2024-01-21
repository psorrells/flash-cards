package com.pamela.flashcards.database.flashcards

import com.pamela.flashcards.model.FlashCardDomain
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

abstract class FlashCardsRepository {
    abstract suspend fun getAllCardsBySetId(setId: UUID): List<FlashCardDomain>

    abstract suspend fun getCardById(id: UUID): FlashCardDomain

    abstract suspend fun getNextDueCardBySetId(setId: UUID): FlashCardDomain?

    abstract suspend fun upsertCardToSet(card: FlashCardDomain, setId: UUID)

    abstract suspend fun deleteCardFromSet(card: FlashCardDomain, setId: UUID)
}

@Singleton
class FlashCardsRepositoryImpl @Inject constructor(
    private val flashCardsDao: FlashCardsDao
) : FlashCardsRepository() {
    override suspend fun getAllCardsBySetId(setId: UUID): List<FlashCardDomain> {
        return flashCardsDao.getAllCardsBySetId(setId.toString()).map { it.toDomain() }
    }

    override suspend fun getCardById(id: UUID): FlashCardDomain {
        return flashCardsDao.getCardById(id.toString()).toDomain()
    }

    override suspend fun getNextDueCardBySetId(setId: UUID): FlashCardDomain? {
        return flashCardsDao.getNextDueCardBySetIdAndDueDate(setId.toString())?.toDomain()
    }

    override suspend fun upsertCardToSet(card: FlashCardDomain, setId: UUID) {
        flashCardsDao.upsert(card.toEntity(setId))
    }

    override suspend fun deleteCardFromSet(card: FlashCardDomain, setId: UUID) {
        flashCardsDao.delete(card.toEntity(setId))
    }

}