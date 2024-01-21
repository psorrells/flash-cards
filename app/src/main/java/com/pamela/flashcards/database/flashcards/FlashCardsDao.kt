package com.pamela.flashcards.database.flashcards

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import java.time.Instant

@Dao
interface FlashCardsDao {
    @Query("SELECT * from cards where set_id = (:setId)")
    suspend fun getAllCardsBySetId(setId: String): List<FlashCardEntity>

    @Query("SELECT * from cards where id = (:id)")
    suspend fun getCardById(id: String): FlashCardEntity

    @Query("SELECT * from cards where set_id = (:setId) and next_due_at <= (:dueDate) order by next_due_at asc, last_studied_at asc LIMIT 1")
    suspend fun getNextDueCardBySetIdAndDueDate(setId: String, dueDate: Long = Instant.now().toEpochMilli()): FlashCardEntity?

    @Upsert
    suspend fun upsert(card: FlashCardEntity)

    @Delete
    suspend fun delete(card: FlashCardEntity)
}