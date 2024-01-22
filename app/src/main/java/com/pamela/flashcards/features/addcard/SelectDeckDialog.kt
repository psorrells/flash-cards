package com.pamela.flashcards.features.addcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.model.FlashCardDeckNameIdDomain
import com.pamela.flashcards.ui.component.StyledTextButton

@Composable
fun SelectDeckDialog(
    options: List<FlashCardDeckNameIdDomain>,
    onSelect: (FlashCardDeckNameIdDomain) -> Unit,
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
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.select_deck),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(options) {
                        Divider(modifier = Modifier.fillMaxWidth())
                        StyledTextButton(
                            onClick = { onSelect(it) },
                            text = it.name,
                            fullWidth = true,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}