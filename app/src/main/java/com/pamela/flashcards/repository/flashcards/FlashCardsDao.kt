package com.pamela.flashcards.repository.flashcards

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashCardsDao {
    @Query("SELECT * from cards where set_id = (:setId)")
    suspend fun getAllCardsBySetId(setId: String): List<FlashCardEntity>

    @Insert
    suspend fun insert(card: FlashCardEntity)

    @Delete
    suspend fun delete(card: FlashCardEntity)
}