package com.pamela.flashcards.database.flashcards

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FlashCardsDao {
    @Query("SELECT * from cards where set_id = (:setId)")
    suspend fun getAllCardsBySetId(setId: String): List<FlashCardEntity>

    @Query("SELECT * from cards where id = (:id)")
    suspend fun getCardById(id: String): FlashCardEntity

    @Upsert
    suspend fun upsert(card: FlashCardEntity)

    @Delete
    suspend fun delete(card: FlashCardEntity)
}