package com.pamela.flashcards.features.addcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.R
import com.pamela.flashcards.model.IncompleteFormError
import com.pamela.flashcards.model.InvalidFlashCardDeckIdError
import com.pamela.flashcards.model.UserErrorException
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.DefaultErrorMessage
import com.pamela.flashcards.ui.component.PopUpFieldWithLabel
import com.pamela.flashcards.ui.component.TextAreaWithLabel
import com.pamela.flashcards.ui.component.TextOnlyErrorBottomSheet
import com.pamela.flashcards.ui.component.StyledTopBar
import com.pamela.flashcards.ui.scaffoldDefaults

@Composable
fun AddCardScreen(viewModel: AddCardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSelectDeckDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { StyledTopBar(titleText = viewModel.getPageTitle(), {}) },
        bottomBar = {
            BottomBarButtonFullWidth(
                onClick = viewModel::saveCard,
                text = stringResource(id = R.string.save),
                icon = Icons.Rounded.Check,
                enabled = uiState.errorState == null || uiState.errorState is UserErrorException
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (uiState.errorState) {
                null, is UserErrorException -> {
                    PopUpFieldWithLabel(
                        label = stringResource(id = R.string.deck_label),
                        value = viewModel.getCurrentSelectedDeckName(),
                        onClick = { showSelectDeckDialog = true }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextAreaWithLabel(
                        label = stringResource(id = R.string.card_front_label),
                        value = uiState.currentCard.front,
                        onChangeValue = { viewModel.updateFlashCard(front = it) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextAreaWithLabel(
                        label = stringResource(id = R.string.card_back_label),
                        value = uiState.currentCard.back,
                        onChangeValue = { viewModel.updateFlashCard(back = it) }
                    )
                }

                else -> DefaultErrorMessage()
            }
        }
        if (uiState.errorState is IncompleteFormError) {
            TextOnlyErrorBottomSheet(text = stringResource(id = R.string.incomplete_add_card_form))
        }
        if (uiState.errorState is InvalidFlashCardDeckIdError) {
            TextOnlyErrorBottomSheet(text = stringResource(id = R.string.invalid_deck_error))
        }
        if (showSelectDeckDialog) {
            SelectDeckDialog(
                options = uiState.allFlashCardDecks,
                onSelect = {
                    viewModel.updateSelectedDeck(it.id)
                    showSelectDeckDialog = false
                },
                onDismiss = { showSelectDeckDialog = false }
            )
        }
    }
}