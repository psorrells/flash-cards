package com.pamela.flashcards.features.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.FlashCardsTheme

@Composable
fun PracticeScreen(viewModel: PracticeScreenViewModel = hiltViewModel()) {
    val card by viewModel.currentCard.collectAsStateWithLifecycle()
    val cardSetName by viewModel.cardSetName.collectAsStateWithLifecycle()
    var isFlipped by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = cardSetName) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                if (isFlipped.not()) {
                    Button(
                        onClick = { isFlipped = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Flip",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Easy",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Medium",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Hard",
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Again",
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FlashCard(isFlipped, { isFlipped = it }, card)
        }
    }
}

@Preview
@Composable
fun OverviewScreenPreview() {
    FlashCardsTheme {
        PracticeScreen(viewModel())
    }
}