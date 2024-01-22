package com.pamela.flashcards.features.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.DeleteFlashCardSetUseCase
import com.pamela.flashcards.domain.GetAllFlashCardSetsUseCase
import com.pamela.flashcards.model.FailedDeleteError
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

    private val _uiState: MutableStateFlow<OverviewUiState> = MutableStateFlow(OverviewUiState())
    val uiState: StateFlow<OverviewUiState> = _uiState

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
            deleteFlashCardSet(set)
                .onSuccess {
                    updateSets()
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(errorState = FailedDeleteError(error))
                    }
                }
        }
    }

    private fun updateSets() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        sets = getAllFlashCardSets().getOrThrow(),
                        errorState = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorState = e)
                }
            }
        }
    }
}

data class OverviewUiState(
    val sets: List<FlashCardSetDomain> = emptyList(),
    val errorState: Throwable? = null
)