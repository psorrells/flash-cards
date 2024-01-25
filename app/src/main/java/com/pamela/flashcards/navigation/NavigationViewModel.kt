package com.pamela.flashcards.navigation

import androidx.lifecycle.ViewModel
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.AddDeckDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.NotificationsSettingsDestination
import com.pamela.flashcards.navigation.OverviewDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateToOverview() {
        navigator.navigateToOverview()
    }

    fun navigateToAddNewDeck() {
        navigator.navigateAndPopUpToOverview(AddDeckDestination.populateRouteWithArgs())
    }

    fun navigateToAddNewCard() {
        navigator.navigateAndPopUpToOverview(AddCardDestination.populateRouteWithArgs())
    }

    fun navigateToNotificationsSettings() {
        navigator.navigateAndPopUpToOverview(NotificationsSettingsDestination.route)
    }
}
