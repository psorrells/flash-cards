package com.pamela.flashcards.features.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.R
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.FailedDeleteError
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.DefaultErrorMessage
import com.pamela.flashcards.ui.component.TextOnlyErrorBottomSheet
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.FlashCardsTheme

@Composable
fun OverviewScreen(viewModel: OverviewViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = { viewModel.initializeState() })
    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = stringResource(id = R.string.overview_header)) },
        bottomBar = {
            BottomBarButtonFullWidth(
                onClick = viewModel::navigateToAddSetScreen,
                text = stringResource(id = R.string.add_new_deck),
                icon = Icons.Rounded.Add
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.errorState) {
                null -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(uiState.decks) { cardSet ->
                            StudySetCard(
                                cardSet,
                                viewModel::navigateToPracticeScreen,
                                viewModel::deleteDeck,
                                viewModel::navigateToAddSetScreen,
                                viewModel::navigateToAddCardScreen
                            )
                        }
                    }
                }

                is EmptyResultError -> EmptyDeckListDisplay()
                is FailedDeleteError -> {
                    TextOnlyErrorBottomSheet(text = stringResource(id = R.string.database_error))
                }

                else -> DefaultErrorMessage()
            }
        }
    }
}

@Preview
@Composable
fun OverviewScreenPreview() {
    FlashCardsTheme {
        OverviewScreen()
    }
}