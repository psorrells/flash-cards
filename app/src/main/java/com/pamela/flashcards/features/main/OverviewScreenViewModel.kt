package com.pamela.flashcards.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.database.flashcardsets.FlashCardSetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewScreenViewModel @Inject constructor(private val flashCardSetsRepository: FlashCardSetsRepository): ViewModel() {

    private val _uiState: MutableStateFlow<List<FlashCardSetDomain>> = MutableStateFlow(listOf(
        FlashCardSetDomain(name = "Northeastern Flowers"),
        FlashCardSetDomain(name = "Temperate Trees"),
        FlashCardSetDomain(name = "True Prairie Grasses")
    ))

    val uiState: StateFlow<List<FlashCardSetDomain>> = _uiState

    fun initializeState() {
        viewModelScope.launch {
            val sets = flashCardSetsRepository.getAllSets()

            if (sets.isNotEmpty()) {
                _uiState.update { sets }
            }
        }
    }

    fun onClickAddSet(name: String = "Butter Scotch Recipes") {
        viewModelScope.launch {
            flashCardSetsRepository.insertSet(
                FlashCardSetDomain(
                name = name
            )
            )
        }
    }
}