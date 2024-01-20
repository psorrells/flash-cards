package com.pamela.flashcards.features.addset

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.GetFlashCardSetByIdUseCase
import com.pamela.flashcards.domain.GetStringResourceUseCase
import com.pamela.flashcards.domain.UpsertFlashCardSetUseCase
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.navigation.AddSetDestination
import com.pamela.flashcards.navigation.Navigator
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddSetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upsertFlashCardSet: UpsertFlashCardSetUseCase,
    private val getFlashCardSetById: GetFlashCardSetByIdUseCase,
    private val getStringResource: GetStringResourceUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSetUiState())
    val uiState = _uiState.asStateFlow()

    private val cardSetId: UUID? by lazy { getUuidOrNull(savedStateHandle[AddSetDestination.cardSetId]) }

    init {
        viewModelScope.launch {
            if (cardSetId != null) {
                getFlashCardSetById(cardSetId!!).onSuccess { flashCardSet ->
                    _uiState.update {
                        it.copy(flashCardSet = flashCardSet)
                    }
                }
            }
        }
    }

    fun getPageTitle(): String {
        return if (cardSetId != null) getStringResource(R.string.edit_set_header)
        else getStringResource(R.string.add_set_header)
    }

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
