package com.pamela.flashcards.repository

import com.pamela.flashcards.repository.flashcards.FlashCardsRepository
import com.pamela.flashcards.repository.flashcards.FlashCardsRepositoryImpl
import com.pamela.flashcards.repository.flashcardsets.FlashCardSetsRepository
import com.pamela.flashcards.repository.flashcardsets.FlashCardSetsRepositoryImpl
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