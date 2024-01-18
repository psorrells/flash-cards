package com.pamela.flashcards.features.addset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.UpsertFlashCardSetUseCase
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSetViewModel @Inject constructor(
    private val upsertFlashCardSet: UpsertFlashCardSetUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSetUiState())
    val uiState = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update {
            val newSet = it.flashCardSet.copy(name = name)
            it.copy(flashCardSet = newSet)
        }
    }

    fun saveSet() {
        viewModelScope.launch {
            upsertFlashCardSet(uiState.value.flashCardSet).onSuccess {
                navigator.popBackStack()
            }
        }
    }
}

data class AddSetUiState(
    val flashCardSet: FlashCardSetDomain = FlashCardSetDomain(name = "")
)
