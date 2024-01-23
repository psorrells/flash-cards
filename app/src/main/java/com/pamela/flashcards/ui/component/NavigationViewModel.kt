package com.pamela.flashcards.ui.component

import androidx.lifecycle.ViewModel
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.AddDeckDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.OverviewDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateToOverview() {
        navigator.navigateTo(OverviewDestination.route)
    }

    fun navigateToAddNewDeck() {
        navigator.navigateTo(AddDeckDestination.populateRouteWithArgs())
    }

    fun navigateToAddNewCard() {
        navigator.navigateTo(AddCardDestination.populateRouteWithArgs())
    }
}
