package com.pamela.flashcards.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pamela.flashcards.database.flashcards.FlashCardEntity
import com.pamela.flashcards.database.flashcards.FlashCardsDao
import com.pamela.flashcards.database.flashcardsets.FlashCardSetEntity
import com.pamela.flashcards.database.flashcardsets.FlashCardSetsDao

@Database(entities = [FlashCardEntity::class, FlashCardSetEntity::class], version = 1)
abstract class FlashCardsDatabase : RoomDatabase() {
    abstract fun flashCardsDao(): FlashCardsDao

    abstract fun flashCardSetsDao(): FlashCardSetsDao
}