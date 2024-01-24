package com.pamela.flashcards.database.decks

import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

interface FlashCardDecksRepository {
    suspend fun getAllDecks(): List<FlashCardDeckDomain>

    suspend fun getAllDecksNameIds(): List<FlashCardDeckNameIdDomain>

    suspend fun getDeckById(id: UUID): FlashCardDeckDomain

    suspend fun upsertDeck(deck: FlashCardDeckDomain)

    suspend fun deleteDeck(deck: FlashCardDeckDomain)
}

@Singleton
class FlashCardDecksRepositoryImpl @Inject constructor(
    private val flashCardDecksDao: FlashCardDecksDao
) : FlashCardDecksRepository {
    override suspend fun getAllDecks(): List<FlashCardDeckDomain> {
        return flashCardDecksDao.getAllDecks().map { it.toDomain() }
    }

    override suspend fun getAllDecksNameIds(): List<FlashCardDeckNameIdDomain> {
        return flashCardDecksDao.getAllDecks().map { it.toNameIdDomain() }
    }

    override suspend fun getDeckById(id: UUID): FlashCardDeckDomain {
        return flashCardDecksDao.getDeckById(id.toString()).toDomain()
    }

    override suspend fun upsertDeck(deck: FlashCardDeckDomain) {
        flashCardDecksDao.upsert(deck.toEntity())
    }

    override suspend fun deleteDeck(deck: FlashCardDeckDomain) {
        flashCardDecksDao.deleteDeck(deck.toEntity())
    }

    private suspend fun FlashCardDeckEntity.toDomain(): FlashCardDeckDomain {
        return this.toDomain(
            size = flashCardDecksDao.getTotalCountForDeck(this.id),
            totalDue = flashCardDecksDao.getTotalDueForDeckByDueDate(
                this.id,
                Instant.now().toEpochMilli()
            ),
            lastStudiedAt = flashCardDecksDao.getLastStudiedAtForDeck(this.id)
        )
    }
}