package com.pamela.flashcards.features.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.FlashCardsTheme

@Composable
fun OverviewScreen(viewModel: OverviewViewModel = hiltViewModel()) {
    val cardSetList by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = { viewModel.initializeState() })
    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = "Study Sets") },
        bottomBar = {
            BottomBarButtonFullWidth(onClick = viewModel::navigateToAddSetScreen) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Add a new set",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(cardSetList) { cardSet ->
                StudySetCard(
                    cardSet,
                    viewModel::navigateToPracticeScreen,
                    viewModel::deleteSet,
                    viewModel::navigateToAddSetScreen,
                    viewModel::navigateToAddCardScreen
                )
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