package com.pamela.flashcards.features.adddeck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.R
import com.pamela.flashcards.model.IncompleteFormError
import com.pamela.flashcards.model.UserErrorException
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.DefaultErrorMessage
import com.pamela.flashcards.ui.component.TextFieldWithLabel
import com.pamela.flashcards.ui.component.TextOnlyErrorBottomSheet
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults

@Composable
fun AddDeckScreen(viewModel: AddDeckViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = viewModel.getPageTitle()) },
        bottomBar = {
            BottomBarButtonFullWidth(
                onClick = viewModel::saveDeck,
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (uiState.errorState) {
                null, is UserErrorException -> {
                    TextFieldWithLabel(
                        label = stringResource(id = R.string.deck_name_label),
                        value = uiState.flashCardDeck.name,
                        onChangeValue = viewModel::updateName
                    )
                }

                else -> DefaultErrorMessage()
            }
        }
        if (uiState.errorState is IncompleteFormError) {
            TextOnlyErrorBottomSheet(text = stringResource(id = R.string.incomplete_add_deck_form))
        }
    }
}