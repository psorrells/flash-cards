package com.pamela.flashcards.database.flashcardsets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface FlashCardSetsDao {
    @Query("SELECT * from sets")
    suspend fun getAllSets(): List<FlashCardSetEntity>

    @Query("SELECT COUNT(*) from cards where set_id = (:setId)")
    suspend fun getTotalCountForSet(setId: String): Int

    @Query("SELECT COUNT(*) from cards where set_id = (:setId) AND next_due_at <= (:dueDate)")
    suspend fun getTotalDueForSetByDueDate(setId: String, dueDate: Long): Int

    @Query("SELECT 1 from cards where set_id = (:setId) order by last_studied_at desc")
    suspend fun getLastStudiedAtForSet(setId: String): Long

    @Insert
    suspend fun insert(card: FlashCardSetEntity)

    @Query("DELETE from cards where set_id = (:setId)")
    suspend fun deleteAllCardsInSet(setId: String)

    @Query("DELETE from sets where id = (:setId)")
    suspend fun deleteSetById(setId: String)

    @Transaction
    suspend fun deleteSet(set: FlashCardSetEntity) {
        deleteAllCardsInSet(set.id)
        deleteSetById(set.id)
    }
}