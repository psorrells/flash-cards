package com.pamela.flashcards.features.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostControllerViewModel @Inject constructor(
    val navigator: Navigator
): ViewModel() {
    fun getStartDestination(): String {
        return OverviewDestination.route
    }
}