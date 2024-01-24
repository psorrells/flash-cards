package com.pamela.flashcards.features.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.DeleteFlashCardUseCase
import com.pamela.flashcards.domain.GetFlashCardDeckByIdUseCase
import com.pamela.flashcards.domain.GetNextDueCardByDeckIdUseCase
import com.pamela.flashcards.domain.UpdateFlashCardStatsUseCase
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.model.GetNewCardException
import com.pamela.flashcards.model.MissingSavedStateError
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.NavDrawerDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.PracticeDestination
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFlashCardDeckById: GetFlashCardDeckByIdUseCase,
    private val getNextDueCardByDeckId: GetNextDueCardByDeckIdUseCase,
    private val updateFlashCardStats: UpdateFlashCardStatsUseCase,
    private val deleteFlashCard: DeleteFlashCardUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState: MutableStateFlow<PracticeUiState> =
        MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    private val deckId: UUID? by lazy { getUuidOrNull(savedStateHandle[PracticeDestination.cardDeckId]) }
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update { state ->
            state.copy(errorState = throwable, isFlipped = false)
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            if (deckId == null) throw MissingSavedStateError(PracticeDestination.cardDeckId)
            deckId?.let { deckId ->
                val cardSetResult = async { getFlashCardDeckById(deckId) }
                val currentCardResult = async { getNextDueCardByDeckId(deckId) }
                _uiState.update {
                    it.copy(
                        cardSet = cardSetResult.await().getOrThrow(),
                        currentCard = currentCardResult.await().getOrThrow(),
                        errorState = null
                    )
                }
            }
        }
    }

    fun navigateToAddCard() {
        deckId?.let {
            navigator.navigateTo(AddCardDestination.populateRouteWithArgs(cardDeckId = it.toString()))
        }
    }

    fun navigateToEditCard() {
        deckId?.let {
            navigator.navigateTo(
                AddCardDestination.populateRouteWithArgs(
                    cardDeckId = it.toString(),
                    cardId = uiState.value.currentCard.id.toString()
                )
            )
        }
    }

    fun openNavDrawer() {
        navigator.navigateTo(NavDrawerDestination.route)
    }

    fun setIsFlipped(isFlipped: Boolean) {
        _uiState.update {
            it.copy(isFlipped = isFlipped)
        }
    }

    fun updateFlashCardWithDifficulty(difficulty: Difficulty) {
        viewModelScope.launch(exceptionHandler) {
            updateFlashCardStats(uiState.value.currentCard, difficulty).getOrThrow()
            throw GetNewCardException()
        }
    }

    fun setCurrentCardWithNextDueCard() {
        viewModelScope.launch(exceptionHandler) {
            getNextDueCardByDeckId(uiState.value.cardSet.id)
                .getOrThrow()
                .let { card ->
                    _uiState.update { state ->
                        state.copy(currentCard = card, errorState = null, isFlipped = false)
                    }
                }
        }
    }

    fun deleteCurrentCard() {
        viewModelScope.launch(exceptionHandler) {
            deleteFlashCard(uiState.value.currentCard).getOrThrow()
            setCurrentCardWithNextDueCard()
        }
    }
}

data class PracticeUiState(
    val currentCard: FlashCardDomain = FlashCardDomain(),
    val cardSet: FlashCardDeckDomain = FlashCardDeckDomain(),
    val isFlipped: Boolean = false,
    val errorState: Throwable? = null
)