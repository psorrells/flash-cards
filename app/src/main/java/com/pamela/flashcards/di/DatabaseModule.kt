package com.pamela.flashcards.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.pamela.flashcards.database.FlashCardsDatabase
import com.pamela.flashcards.database.flashcards.FlashCardsDao
import com.pamela.flashcards.database.decks.FlashCardDecksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideFlashCardsDao(flashCardsDatabase: FlashCardsDatabase): FlashCardsDao {
        return flashCardsDatabase.flashCardsDao()
    }

    @Provides
    fun provideFlashCardDecksDao(flashCardsDatabase: FlashCardsDatabase): FlashCardDecksDao {
        return flashCardsDatabase.flashCardDecksDao()
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