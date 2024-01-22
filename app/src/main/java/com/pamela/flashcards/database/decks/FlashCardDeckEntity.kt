package com.pamela.flashcards.database.decks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import java.time.Instant
import java.util.UUID

@Entity(tableName = "decks")
data class FlashCardDeckEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
) {
    fun toDomain(size: Int, totalDue: Int, lastStudiedAt: Long?): FlashCardDeckDomain {
        return FlashCardDeckDomain(
            id = UUID.fromString(id),
            name = name,
            size = size,
            totalDue = totalDue,
            createdAt = Instant.ofEpochMilli(createdAt),
            lastStudiedAt = lastStudiedAt?.let { Instant.ofEpochMilli(it) }
        )
    }

    fun toNameIdDomain(): FlashCardDeckNameIdDomain {
        return FlashCardDeckNameIdDomain(
            id = UUID.fromString(id),
            name = name
        )
    }
}
