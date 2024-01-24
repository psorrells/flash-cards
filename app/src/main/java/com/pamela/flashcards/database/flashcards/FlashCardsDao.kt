package com.pamela.flashcards.database.flashcards

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import java.time.Instant

@Dao
interface FlashCardsDao {
    @Query("SELECT * from cards where deck_id = (:deckId)")
    suspend fun getAllCardsByDeckId(deckId: String): List<FlashCardEntity>

    @Query("SELECT * from cards where id = (:id)")
    suspend fun getCardById(id: String): FlashCardEntity

    @Query("SELECT * from cards where deck_id = (:deckId) and next_due_at <= (:dueDate) order by next_due_at asc, last_studied_at asc LIMIT 1")
    suspend fun getNextDueCardByDeckIdAndDueDate(deckId: String, dueDate: Long = Instant.now().toEpochMilli()): FlashCardEntity?

    @Query("SELECT * from cards where next_due_at <= (:dueDate) order by next_due_at asc, last_studied_at asc LIMIT 1")
    suspend fun getNextDueCardByDueDate(dueDate: Long = Instant.now().toEpochMilli()): FlashCardEntity?

    @Upsert
    suspend fun upsert(card: FlashCardEntity)

    @Upsert
    suspend fun upsertAll(vararg cards: FlashCardEntity)

    @Delete
    suspend fun delete(card: FlashCardEntity)

    @Query("DELETE from cards where id = (:id)")
    suspend fun deleteCardById(id: String)
}