package com.pamela.flashcards.features.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.GetFlashCardSetByIdUseCase
import com.pamela.flashcards.domain.GetNextDueCardBySetIdUseCase
import com.pamela.flashcards.domain.UpdateFlashCardStatsUseCase
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.model.GetNewCardException
import com.pamela.flashcards.model.MissingSavedStateError
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.PracticeDestination
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFlashCardSetById: GetFlashCardSetByIdUseCase,
    private val getNextDueCardBySetId: GetNextDueCardBySetIdUseCase,
    private val updateFlashCardStats: UpdateFlashCardStatsUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState: MutableStateFlow<PracticeUiState> =
        MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    private val cardSetId: UUID? by lazy { getUuidOrNull(savedStateHandle[PracticeDestination.cardSetId]) }

    init {
        viewModelScope.launch {
            cardSetId?.let { setId ->
                try {
                    val cardSetResult = async { getFlashCardSetById(setId) }
                    val currentCardResult = async { getNextDueCardBySetId(setId) }
                    _uiState.update {
                        it.copy(
                            cardSet = cardSetResult.await().getOrThrow(),
                            currentCard = currentCardResult.await().getOrThrow(),
                            errorState = null
                        )
                    }
                } catch (e: Exception) {
                    setErrorState(e)
                }
            }
            if (cardSetId == null) {
                setErrorState(MissingSavedStateError(PracticeDestination.cardSetId))
            }
        }
    }

    fun navigateToAddCard() {
        cardSetId?.let {
            navigator.navigateTo(AddCardDestination.populateRouteWithArgs(cardSetId = it.toString()))
        }
    }

    fun setIsFlipped(isFlipped: Boolean) {
        _uiState.update {
            it.copy(isFlipped = isFlipped)
        }
    }

    fun updateFlashCardWithDifficulty(difficulty: Difficulty) {
        viewModelScope.launch {
                updateFlashCardStats(
                    uiState.value.currentCard,
                    uiState.value.cardSet.id,
                    difficulty
                ).onSuccess {
                    setErrorState(GetNewCardException())
                }.onFailure { error ->
                    setErrorState(error)
                }
        }
    }

    fun setCurrentCardWithNextDueCard() {
        viewModelScope.launch {
            getNextDueCardBySetId(uiState.value.cardSet.id).onSuccess { card ->
                _uiState.update { state ->
                    state.copy(currentCard = card, errorState = null, isFlipped = false)
                }
            }.onFailure { error ->
                setErrorState(error)
            }
        }
    }

    private fun setErrorState(error: Throwable) {
        _uiState.update { state ->
            state.copy(errorState = error, isFlipped = false)
        }
    }
}

data class PracticeUiState(
    val currentCard: FlashCardDomain = FlashCardDomain(),
    val cardSet: FlashCardSetDomain = FlashCardSetDomain(name = ""),
    val isFlipped: Boolean = false,
    val errorState: Throwable? = null
)