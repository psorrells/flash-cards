package com.pamela.flashcards.features.addcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.model.FlashCardSetNameIdDomain

@Composable
fun SelectSetDialog(
    options: List<FlashCardSetNameIdDomain>,
    onSelect: (FlashCardSetNameIdDomain) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
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
                Text(
                    text = stringResource(id = R.string.select_set),
                    style = MaterialTheme.typography.labelLarge
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(options) {
                        Divider(modifier = Modifier.fillMaxWidth())
                        TextButton(onClick = { onSelect(it) }) {
                            Text(text = it.name, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}