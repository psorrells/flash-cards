package com.pamela.flashcards.di

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.database.flashcards.FlashCardsRepositoryImpl
import com.pamela.flashcards.database.decks.FlashCardDecksRepository
import com.pamela.flashcards.database.decks.FlashCardDecksRepositoryImpl
import com.pamela.flashcards.database.preferences.PreferencesRepository
import com.pamela.flashcards.database.preferences.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideFlashCardsRepository(impl: FlashCardsRepositoryImpl): FlashCardsRepository

    @Binds
    fun provideFlashCardDecksRepository(impl: FlashCardDecksRepositoryImpl): FlashCardDecksRepository

    @Binds
    fun providePreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}