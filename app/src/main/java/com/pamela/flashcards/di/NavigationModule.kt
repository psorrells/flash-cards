package com.pamela.flashcards.di

import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface NavigationModule {
    @Binds
    @Singleton
    fun provideNavigator(navigatorImpl: NavigatorImpl): Navigator
}