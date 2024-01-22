package com.pamela.flashcards.features.addcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.GetAllFlashCardDecksNameIdUseCase
import com.pamela.flashcards.domain.GetFlashCardByIdUseCase
import com.pamela.flashcards.domain.GetStringResourceUseCase
import com.pamela.flashcards.domain.UpsertFlashCardToDeckUseCase
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import com.pamela.flashcards.navigation.AddCardDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val upsertFlashCardToSet: UpsertFlashCardToDeckUseCase,
    private val getStringResource: GetStringResourceUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCardUiState())
    val uiState = _uiState.asStateFlow()

    private val deckId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddCardDestination.cardDeckId]) }
    private val cardId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddCardDestination.cardId]) }

    init {
        viewModelScope.launch {
            val flashCardDecksResult = async { getAllFlashCardSetsNameId() }
            val flashCardResult = async { cardId?.let { getFlashCardById(it) } }
            try {
                val decks = flashCardDecksResult.await().getOrThrow()
                val card = flashCardResult.await()?.getOrThrow()
                _uiState.update { state ->
                    state.copy(
                        currentCard = card ?: FlashCardDomain(),
                        selectedDeckId = card?.id ?: deckId,
                        allFlashCardDecks = decks
                    )
                }
            } catch (e: Exception) {
                // TODO: show error?
            }
        }
    }

    fun getPageTitle(): String {
        return if (cardId != null) getStringResource(R.string.edit_card_header)
        else getStringResource(R.string.add_card_header)
    }

    fun getCurrentSelectedDeckName(): String {
        return uiState.value.allFlashCardDecks.find {
            it.id == uiState.value.selectedDeckId
        }?.name ?: ""
    }

    fun updateSelectedDeck(deckId: UUID) {
        _uiState.update {
            it.copy(selectedDeckId = deckId)
        }
    }

    fun updateFlashCard(
        front: String? = null,
        back: String? = null
    ) {
        val newFlashCard = uiState.value.currentCard.copy(
            front = front ?: uiState.value.currentCard.front,
            back = back ?: uiState.value.currentCard.back,
        )
        _uiState.update {
            it.copy(currentCard = newFlashCard)
        }
    }

    fun saveCard() {
        viewModelScope.launch {
            if (uiState.value.selectedDeckId != null) {
                upsertFlashCardToSet(
                    uiState.value.currentCard,
                    uiState.value.selectedDeckId!!
                ).onSuccess {
                    navigator.popBackStack()
                }
            }
        }
    }
}

data class AddCardUiState(
    val selectedDeckId: UUID? = null,
    val allFlashCardDecks: List<FlashCardDeckNameIdDomain> = listOf(),
    val currentCard: FlashCardDomain = FlashCardDomain()
)
