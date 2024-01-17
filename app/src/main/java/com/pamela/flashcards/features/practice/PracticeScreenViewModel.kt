package com.pamela.flashcards.features.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.repository.flashcards.FlashCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PracticeScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val flashCardsRepository: FlashCardsRepository
): ViewModel() {

    private val _currentCard: MutableStateFlow<FlashCardDomain> = MutableStateFlow(FlashCardDomain(front = "What is this"))
    val currentCard: StateFlow<FlashCardDomain> = _currentCard
    private val _cardSetName: MutableStateFlow<String> = MutableStateFlow("CardSetId:")
    val cardSetName: StateFlow<String> = _cardSetName

    private val cardSetId: String? by lazy { savedStateHandle["cardSetId"] }

    init {
        _cardSetName.update {
            "CardSetId: $cardSetId"
        }

        viewModelScope.launch {
            val list = flashCardsRepository.getAllCardsBySetId(UUID.fromString(cardSetId))

            if (list.isNotEmpty()) {
                _currentCard.update { list.first() }
            }
        }
    }
}