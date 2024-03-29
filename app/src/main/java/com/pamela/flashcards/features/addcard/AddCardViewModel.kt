package com.pamela.flashcards.features.addcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.deck.GetAllFlashCardDecksNameIdUseCase
import com.pamela.flashcards.domain.flashcard.GetFlashCardByIdUseCase
import com.pamela.flashcards.domain.util.GetStringResourceUseCase
import com.pamela.flashcards.domain.flashcard.UpsertFlashCardUseCase
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import com.pamela.flashcards.model.IncompleteFormError
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.NavDrawerDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllFlashCardSetsNameId: GetAllFlashCardDecksNameIdUseCase,
    private val getFlashCardById: GetFlashCardByIdUseCase,
    private val upsertFlashCard: UpsertFlashCardUseCase,
    private val getStringResource: GetStringResourceUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCardUiState())
    val uiState = _uiState.asStateFlow()

    private val deckId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddCardDestination.cardDeckId]) }
    private val cardId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddCardDestination.cardId]) }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update {
            it.copy(errorState = throwable)
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            val flashCardDecksResult = async { getAllFlashCardSetsNameId() }
            val flashCardResult = async { cardId?.let { getFlashCardById(it) } }
            val decks = flashCardDecksResult.await().getOrThrow()
            val card = flashCardResult.await()?.getOrThrow()
            _uiState.update { state ->
                state.copy(
                    currentCard = card ?: FlashCardDomain(deckId = deckId ?: UUID.randomUUID()),
                    allFlashCardDecks = decks,
                    errorState = null
                )
            }
        }
    }

    fun getPageTitle(): String {
        return if (cardId != null) getStringResource(R.string.edit_card_header)
        else getStringResource(R.string.add_card_header)
    }

    fun getCurrentSelectedDeckName(): String {
        return uiState.value.allFlashCardDecks.find {
            it.id == uiState.value.currentCard.deckId
        }?.name ?: ""
    }

    fun updateFlashCard(
        front: String? = null,
        back: String? = null,
        deckId: UUID? = null
    ) {
        val newFlashCard = uiState.value.currentCard.copy(
            front = front ?: uiState.value.currentCard.front,
            back = back ?: uiState.value.currentCard.back,
            deckId = deckId ?: uiState.value.currentCard.deckId
        )
        _uiState.update {
            it.copy(currentCard = newFlashCard, errorState = null)
        }
    }

    fun saveCard() {
        viewModelScope.launch(exceptionHandler) {
            if (
                uiState.value.currentCard.front.isBlank() ||
                uiState.value.currentCard.back.isBlank() ||
                getCurrentSelectedDeckName().isBlank()
            ) throw IncompleteFormError()
            upsertFlashCard(uiState.value.currentCard).getOrThrow()
            navigator.navigateToOverview()
        }
    }

    fun openNavDrawer() {
        navigator.navigateTo(NavDrawerDestination.route)
    }
}

data class AddCardUiState(
    val allFlashCardDecks: List<FlashCardDeckNameIdDomain> = listOf(),
    val currentCard: FlashCardDomain = FlashCardDomain(),
    val errorState: Throwable? = null
)
