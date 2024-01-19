package com.pamela.flashcards.database.flashcardsets

import com.pamela.flashcards.model.FlashCardSetDomain
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

abstract class FlashCardSetsRepository {
    abstract suspend fun getAllSets(): List<FlashCardSetDomain>

    abstract suspend fun getSetById(setId: UUID): FlashCardSetDomain

    abstract suspend fun upsertSet(set: FlashCardSetDomain)

    abstract suspend fun deleteSet(set: FlashCardSetDomain)
}

@Singleton
class FlashCardSetsRepositoryImpl @Inject constructor(
    private val flashCardSetsDao: FlashCardSetsDao
) : FlashCardSetsRepository() {
    override suspend fun getAllSets(): List<FlashCardSetDomain> {
        return flashCardSetsDao.getAllSets().map { it.toDomain() }
    }

    override suspend fun getSetById(setId: UUID): FlashCardSetDomain {
        return flashCardSetsDao.getSetById(setId.toString()).toDomain()
    }

    override suspend fun upsertSet(set: FlashCardSetDomain) {
        flashCardSetsDao.upsert(set.toEntity())
    }

    override suspend fun deleteSet(set: FlashCardSetDomain) {
        flashCardSetsDao.deleteSet(set.toEntity())
    }

    private suspend fun FlashCardSetEntity.toDomain(): FlashCardSetDomain {
        return this.toDomain(
            size = flashCardSetsDao.getTotalCountForSet(this.id),
            totalDue = flashCardSetsDao.getTotalDueForSetByDueDate(
                this.id,
                Instant.now().toEpochMilli()
            ),
            lastStudiedAt = flashCardSetsDao.getLastStudiedAtForSet(this.id)
        )
    }
}