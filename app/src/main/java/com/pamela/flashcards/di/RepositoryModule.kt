package com.pamela.flashcards.di

import com.pamela.flashcards.database.flashcards.FlashCardsRepository
import com.pamela.flashcards.database.flashcards.FlashCardsRepositoryImpl
import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideFlashCardsRepository(impl: FlashCardsRepositoryImpl): FlashCardsRepository

    @Binds
    fun provideFlashCardSetsRepository(impl: FlashCardSetsRepositoryImpl): FlashCardSetsRepository
}