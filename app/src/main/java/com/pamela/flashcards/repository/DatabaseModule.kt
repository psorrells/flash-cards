package com.pamela.flashcards.repository

import android.content.Context
import androidx.room.Room
import com.pamela.flashcards.repository.flashcards.FlashCardsDao
import com.pamela.flashcards.repository.flashcardsets.FlashCardSetsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFlashCardsDao(flashCardsDatabase: FlashCardsDatabase): FlashCardsDao {
        return flashCardsDatabase.flashCardsDao()
    }

    @Provides
    fun provideFlashCardSetsDao(flashCardsDatabase: FlashCardsDatabase): FlashCardSetsDao {
        return flashCardsDatabase.flashCardSetsDao()
    }

    @Provides
    @Singleton
    fun provideFlashCardsDatabase(@ApplicationContext appContext: Context): FlashCardsDatabase {
        return Room.databaseBuilder(
            appContext,
            FlashCardsDatabase::class.java,
            "flash_cards"
        ).build()
    }
}