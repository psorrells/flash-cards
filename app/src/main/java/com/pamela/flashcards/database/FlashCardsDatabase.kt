package com.pamela.flashcards.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pamela.flashcards.database.flashcards.FlashCardEntity
import com.pamela.flashcards.database.flashcards.FlashCardsDao
import com.pamela.flashcards.database.decks.FlashCardDeckEntity
import com.pamela.flashcards.database.decks.FlashCardDecksDao

@Database(entities = [FlashCardEntity::class, FlashCardDeckEntity::class], version = 1)
abstract class FlashCardsDatabase : RoomDatabase() {
    abstract fun flashCardsDao(): FlashCardsDao

    abstract fun flashCardDecksDao(): FlashCardDecksDao
}