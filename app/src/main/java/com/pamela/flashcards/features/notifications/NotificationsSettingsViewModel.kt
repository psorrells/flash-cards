package com.pamela.flashcards.features.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.CreateNextDueCardFrontNotificationUseCase
import com.pamela.flashcards.navigation.NavDrawerDestination
import com.pamela.flashcards.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsSettingsViewModel @Inject constructor(
    private val createNextDueCardNotification: CreateNextDueCardFrontNotificationUseCase,
    private val navigator: Navigator
) : ViewModel() {
    fun openNavDrawer() {
        navigator.navigateTo(NavDrawerDestination.route)
    }

    fun testNotification() {
        viewModelScope.launch {
            createNextDueCardNotification()
        }
    }

    fun saveSettings() {
        // TODO
    }
}
