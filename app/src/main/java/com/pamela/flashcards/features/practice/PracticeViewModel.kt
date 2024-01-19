package com.pamela.flashcards.features.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.GetFlashCardSetByIdUseCase
import com.pamela.flashcards.domain.GetFlashCardsBySetIdUseCase
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.PracticeDestination
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFlashCardSetById: GetFlashCardSetByIdUseCase,
    private val getFlashCardsBySetId: GetFlashCardsBySetIdUseCase,
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
                    val listResult = async { getFlashCardsBySetId(setId) }
                    _uiState.update {
                        it.copy(
                            cardSet = cardSetResult.await().getOrThrow(),
                            currentCard = listResult.await().getOrThrow().first()
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

    fun navigateToAddCard() {
        cardSetId?.let {
            navigator.navigateTo(AddCardDestination.populateRouteWithArgs(cardSetId = it.toString()))
        }
    }
}

data class PracticeUiState(
    val currentCard: FlashCardDomain = FlashCardDomain(),
    val cardSet: FlashCardSetDomain = FlashCardSetDomain(name = ""),
    val errorState: Throwable? = null
)