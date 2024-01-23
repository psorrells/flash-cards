package com.pamela.flashcards.features.notifications

import androidx.lifecycle.ViewModel
import com.pamela.flashcards.navigation.NavDrawerDestination
import com.pamela.flashcards.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsSettingsViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel() {
    fun openNavDrawer() {
        navigator.navigateTo(NavDrawerDestination.route)
    }

    fun saveSettings() {
        // TODO
    }
}
