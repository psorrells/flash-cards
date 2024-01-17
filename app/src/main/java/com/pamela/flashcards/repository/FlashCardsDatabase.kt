package com.pamela.flashcards.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pamela.flashcards.repository.flashcards.FlashCardEntity
import com.pamela.flashcards.repository.flashcards.FlashCardsDao
import com.pamela.flashcards.repository.flashcardsets.FlashCardSetEntity
import com.pamela.flashcards.repository.flashcardsets.FlashCardSetsDao

@Database(entities = [FlashCardEntity::class, FlashCardSetEntity::class], version = 1)
abstract class FlashCardsDatabase : RoomDatabase() {
    abstract fun flashCardsDao(): FlashCardsDao

    abstract fun flashCardSetsDao(): FlashCardSetsDao
}