package com.pamela.flashcards.database.flashcardsets

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface FlashCardSetsDao {
    @Query("SELECT * from sets")
    suspend fun getAllSets(): List<FlashCardSetEntity>

    @Query("SELECT * from sets where id = (:setId) limit 1")
    suspend fun getSetById(setId: String): FlashCardSetEntity

    @Query("SELECT COUNT(*) from cards where set_id = (:setId)")
    suspend fun getTotalCountForSet(setId: String): Int

    @Query("SELECT COUNT(*) from cards where set_id = (:setId) AND next_due_at <= (:dueDate)")
    suspend fun getTotalDueForSetByDueDate(setId: String, dueDate: Long): Int

    @Query("SELECT last_studied_at from cards where set_id = (:setId) order by last_studied_at desc limit 1")
    suspend fun getLastStudiedAtForSet(setId: String): Long?

    @Upsert
    suspend fun upsert(card: FlashCardSetEntity)

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