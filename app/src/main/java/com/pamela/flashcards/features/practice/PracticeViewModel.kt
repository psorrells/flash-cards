package com.pamela.flashcards.features.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.GetFlashCardsBySetIdUseCase
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.navigation.PracticeDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFlashCardsBySetId: GetFlashCardsBySetIdUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _currentCard: MutableStateFlow<FlashCardDomain> =
        MutableStateFlow(FlashCardDomain(front = "What is this"))
    val currentCard: StateFlow<FlashCardDomain> = _currentCard
    private val _cardSetName: MutableStateFlow<String> = MutableStateFlow("CardSetId:")
    val cardSetName: StateFlow<String> = _cardSetName

    private val cardSetId: String? by lazy { savedStateHandle[PracticeDestination.cardSetId] }

    init {
        _cardSetName.update {
            "CardSetId: $cardSetId"
        }

        viewModelScope.launch {
            val list = getFlashCardsBySetId(UUID.fromString(cardSetId)).getOrNull()

            if (list?.isNotEmpty() == true) _currentCard.update { list.first() }
        }
    }

    fun onClickBack() {
        navigator.popBackStack()
    }
}