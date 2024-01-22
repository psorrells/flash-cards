package com.pamela.flashcards.database.decks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface FlashCardDecksDao {
    @Query("SELECT * from decks")
    suspend fun getAllDecks(): List<FlashCardDeckEntity>

    @Query("SELECT * from decks where id = (:id) limit 1")
    suspend fun getDeckById(id: String): FlashCardDeckEntity

    @Query("SELECT COUNT(*) from cards where deck_id = (:id)")
    suspend fun getTotalCountForDeck(id: String): Int

    @Query("SELECT COUNT(*) from cards where deck_id = (:id) AND next_due_at <= (:dueDate)")
    suspend fun getTotalDueForDeckByDueDate(id: String, dueDate: Long): Int

    @Query("SELECT last_studied_at from cards where deck_id = (:id) order by last_studied_at desc limit 1")
    suspend fun getLastStudiedAtForDeck(id: String): Long?

    @Upsert
    suspend fun upsert(deck: FlashCardDeckEntity)

    @Query("DELETE from cards where deck_id = (:deckId)")
    suspend fun deleteAllCardsInDeck(deckId: String)

    @Query("DELETE from decks where id = (:id)")
    suspend fun deleteDeckById(id: String)

    @Transaction
    suspend fun deleteDeck(deck: FlashCardDeckEntity) {
        deleteAllCardsInDeck(deck.id)
        deleteDeckById(deck.id)
    }
}