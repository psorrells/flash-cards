package com.pamela.flashcards.features.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.DeleteFlashCardSetUseCase
import com.pamela.flashcards.domain.GetAllFlashCardSetsUseCase
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.AddSetDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.PracticeDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getAllFlashCardSets: GetAllFlashCardSetsUseCase,
    private val deleteFlashCardSet: DeleteFlashCardSetUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<FlashCardSetDomain>> = MutableStateFlow(listOf())
    val uiState: StateFlow<List<FlashCardSetDomain>> = _uiState

    fun initializeState() {
        updateSets()
    }

    fun navigateToPracticeScreen(cardSet: FlashCardSetDomain) {
        navigator.navigateTo(PracticeDestination.populateRouteWithArgs(cardSet.id.toString()))
    }

    fun navigateToAddSetScreen(cardSet: FlashCardSetDomain? = null) {
        navigator.navigateTo(AddSetDestination.populateRouteWithArgs(cardSet?.id.toString()))
    }

    fun navigateToAddCardScreen(cardSet: FlashCardSetDomain? = null) {
        navigator.navigateTo(AddCardDestination.populateRouteWithArgs(cardSetId = cardSet?.id.toString()))
    }

    fun deleteSet(set: FlashCardSetDomain) {
        viewModelScope.launch {
            deleteFlashCardSet(set).onSuccess {
                updateSets()
            }
        }
    }

    private fun updateSets() {
        viewModelScope.launch {
            val sets = getAllFlashCardSets().getOrNull()

            if (sets?.isNotEmpty() == true) _uiState.update { sets }
            else _uiState.update { listOf() }
        }
    }
}