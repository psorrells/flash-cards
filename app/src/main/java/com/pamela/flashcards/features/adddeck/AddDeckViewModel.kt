package com.pamela.flashcards.features.adddeck

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.GetFlashCardDeckByIdUseCase
import com.pamela.flashcards.domain.GetStringResourceUseCase
import com.pamela.flashcards.domain.UpsertFlashCardDeckUseCase
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.model.IncompleteFormError
import com.pamela.flashcards.navigation.AddDeckDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddDeckViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upsertFlashCardDeck: UpsertFlashCardDeckUseCase,
    private val getFlashCardDeckById: GetFlashCardDeckByIdUseCase,
    private val getStringResource: GetStringResourceUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDeckUiState())
    val uiState = _uiState.asStateFlow()

    private val deckId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddDeckDestination.cardDeckId]) }

    init {
        viewModelScope.launch {
            if (deckId != null) {
                getFlashCardDeckById(deckId!!)
                    .onSuccess { flashCardDeck ->
                        _uiState.update {
                            it.copy(flashCardDeck = flashCardDeck)
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(errorState = error)
                        }
                    }
            }
        }
    }

    fun getPageTitle(): String {
        return if (deckId != null) getStringResource(R.string.edit_deck_header)
        else getStringResource(R.string.add_deck_header)
    }

    fun updateName(name: String) {
        _uiState.update {
            val newDeck = it.flashCardDeck.copy(name = name)
            it.copy(flashCardDeck = newDeck, errorState = null)
        }
    }

    fun saveDeck() {
        if (uiState.value.flashCardDeck.name.isBlank()) {
            _uiState.update {
                it.copy(errorState = IncompleteFormError())
            }
            return
        }
        viewModelScope.launch {
            upsertFlashCardDeck(uiState.value.flashCardDeck).onSuccess {
                navigator.popBackStack()
            }
        }
    }
}

data class AddDeckUiState(
    val flashCardDeck: FlashCardDeckDomain = FlashCardDeckDomain(name = ""),
    val errorState: Throwable? = null
)
