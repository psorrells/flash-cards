package com.pamela.flashcards.database.flashcardsets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.model.FlashCardSetNameIdDomain
import java.time.Instant
import java.util.UUID

@Entity(tableName = "sets")
data class FlashCardSetEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
) {
    fun toDomain(size: Int, totalDue: Int, lastStudiedAt: Long?): FlashCardSetDomain {
        return FlashCardSetDomain(
            id = UUID.fromString(id),
            name = name,
            size = size,
            totalDue = totalDue,
            createdAt = Instant.ofEpochMilli(createdAt),
            lastStudiedAt = lastStudiedAt?.let { Instant.ofEpochMilli(it) }
        )
    }

    fun toNameIdDomain(): FlashCardSetNameIdDomain {
        return FlashCardSetNameIdDomain(
            id = UUID.fromString(id),
            name = name
        )
    }
}
