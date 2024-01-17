package com.pamela.flashcards.repository.flashcardsets

import com.pamela.flashcards.model.FlashCardSetDomain
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

abstract class FlashCardSetsRepository {
    abstract suspend fun getAllSets(): List<FlashCardSetDomain>

    abstract suspend fun insertSet(set: FlashCardSetDomain)

    abstract suspend fun deleteSet(set: FlashCardSetDomain)
}

@Singleton
class FlashCardSetsRepositoryImpl @Inject constructor(
    private val flashCardSetsDao: FlashCardSetsDao
) : FlashCardSetsRepository() {
    override suspend fun getAllSets(): List<FlashCardSetDomain> {
        return flashCardSetsDao.getAllSets().map {
            it.toDomain(
                size = flashCardSetsDao.getTotalCountForSet(it.id),
                totalDue = flashCardSetsDao.getTotalDueForSetByDueDate(it.id, Instant.now().toEpochMilli()),
                lastStudiedAt = flashCardSetsDao.getLastStudiedAtForSet(it.id)
            )
        }
    }

    override suspend fun insertSet(set: FlashCardSetDomain) {
        flashCardSetsDao.insert(set.toEntity())
    }

    override suspend fun deleteSet(set: FlashCardSetDomain) {
        flashCardSetsDao.deleteSet(set.toEntity())
    }

}