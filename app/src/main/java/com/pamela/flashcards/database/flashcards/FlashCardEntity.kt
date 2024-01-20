package com.pamela.flashcards.database.flashcards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pamela.flashcards.model.FlashCardDomain
import java.time.Instant
import java.util.UUID

@Entity(tableName = "cards")
data class FlashCardEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "set_id") val setId: String,
    @ColumnInfo(name = "front") val front: String,
    @ColumnInfo(name = "back") val back: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "last_studied_at") val lastStudiedAt: Long?,
    @ColumnInfo(name = "last_interval_in_millis") val lastIntervalInMillis: Long,
    @ColumnInfo(name = "next_due_at") val nextDueAt: Long
) {
    fun toDomain(): FlashCardDomain {
        return FlashCardDomain(
            id = UUID.fromString(id),
            front = front,
            back = back,
            createdAt = Instant.ofEpochMilli(createdAt),
            lastStudiedAt = lastStudiedAt?.let { Instant.ofEpochMilli(it) },
            lastIntervalInMillis = lastIntervalInMillis,
            nextDueAt = Instant.ofEpochMilli(nextDueAt)
        )
    }
}