package com.pamela.flashcards.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.GetAllFlashCardSetsUseCase
import com.pamela.flashcards.domain.InsertFlashCardSetUseCase
import com.pamela.flashcards.features.navigation.Navigator
import com.pamela.flashcards.features.navigation.PracticeDestination
import com.pamela.flashcards.model.FlashCardSetDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewScreenViewModel @Inject constructor(
    private val getAllFlashCardSets: GetAllFlashCardSetsUseCase,
    private val insertFlashCardSet: InsertFlashCardSetUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<FlashCardSetDomain>> = MutableStateFlow(listOf())
    val uiState: StateFlow<List<FlashCardSetDomain>> = _uiState

    fun initializeState() {
        viewModelScope.launch {
            val sets = getAllFlashCardSets().getOrNull()

            if (sets?.isNotEmpty() == true) _uiState.update { sets }
        }
    }

    fun navigateToPracticeScreen(cardSet: FlashCardSetDomain) {
        navigator.navigateTo(PracticeDestination.populateRouteWithArgs(cardSet.id.toString()))
    }

    fun onClickAddSet(name: String = "Butter Scotch Recipes") {
        viewModelScope.launch {
            insertFlashCardSet(FlashCardSetDomain(name = name))
        }
    }
}