package com.pamela.flashcards.features.addcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults

@Composable
fun AddCardScreen(viewModel: AddCardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSelectSetDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = viewModel.getPageTitle()) },
        bottomBar = {
            Box(modifier = Modifier.padding(14.dp)) {
                Button(
                    onClick = viewModel::saveCard,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "Save",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(text = "Set", style = MaterialTheme.typography.labelSmall)
            TextButton(onClick = { showSelectSetDialog = true }) {
                Text(text = viewModel.getCurrentSelectedSetName())
            }
            Text(text = "Card Front", style = MaterialTheme.typography.labelSmall)
            TextField(
                value = uiState.currentCard.front,
                onValueChange = { viewModel.updateFlashCard(front = it) },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Card Back", style = MaterialTheme.typography.labelSmall)
            TextField(
                value = uiState.currentCard.back,
                onValueChange = { viewModel.updateFlashCard(back = it) },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (showSelectSetDialog) {
            Dialog(onDismissRequest = { showSelectSetDialog = false }) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.fillMaxHeight(0.5F)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Select Set", style = MaterialTheme.typography.labelSmall)
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(uiState.allFlashCardSets) {
                                Divider(modifier = Modifier.fillMaxWidth())
                                TextButton(onClick = {
                                    viewModel.updateSelectedSet(it.id)
                                    showSelectSetDialog = false
                                }) {
                                    Text(text = it.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}