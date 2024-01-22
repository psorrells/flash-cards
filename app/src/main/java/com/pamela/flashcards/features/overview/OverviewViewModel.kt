package com.pamela.flashcards.features.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.DeleteFlashCardDeckUseCase
import com.pamela.flashcards.domain.GetAllFlashCardDecksUseCase
import com.pamela.flashcards.model.FailedDeleteError
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.AddDeckDestination
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
    private val getAllFlashCardDecks: GetAllFlashCardDecksUseCase,
    private val deleteFlashCardDeck: DeleteFlashCardDeckUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState: MutableStateFlow<OverviewUiState> = MutableStateFlow(OverviewUiState())
    val uiState: StateFlow<OverviewUiState> = _uiState

    fun initializeState() {
        updateDecks()
    }

    fun navigateToPracticeScreen(cardSet: FlashCardDeckDomain) {
        navigator.navigateTo(PracticeDestination.populateRouteWithArgs(cardSet.id.toString()))
    }

    fun navigateToAddSetScreen(cardSet: FlashCardDeckDomain? = null) {
        navigator.navigateTo(AddDeckDestination.populateRouteWithArgs(cardSet?.id.toString()))
    }

    fun navigateToAddCardScreen(cardSet: FlashCardDeckDomain? = null) {
        navigator.navigateTo(AddCardDestination.populateRouteWithArgs(cardDeckId = cardSet?.id.toString()))
    }

    fun deleteDeck(deck: FlashCardDeckDomain) {
        viewModelScope.launch {
            deleteFlashCardDeck(deck)
                .onSuccess {
                    updateDecks()
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(errorState = FailedDeleteError(error))
                    }
                }
        }
    }

    private fun updateDecks() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        decks = getAllFlashCardDecks().getOrThrow(),
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
    val decks: List<FlashCardDeckDomain> = emptyList(),
    val errorState: Throwable? = null
)