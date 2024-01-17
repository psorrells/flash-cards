package com.pamela.flashcards.features.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.model.FlashCardSetDomain
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun StudySetCard(cardGroup: FlashCardSetDomain, onClickSet: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier.clickable { onClickSet() }
    ) {
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { expanded = expanded.not() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Expand"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = cardGroup.name
                )
                if (expanded) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "${cardGroup.totalDue}/${cardGroup.size} cards due"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "Last studied at: ${
                            if (cardGroup.lastStudiedAt != null)
                                LocalDateTime.ofInstant(
                                    cardGroup.lastStudiedAt,
                                    ZoneId.systemDefault()
                                )
                            else "Never :("
                        }"
                    )
                }
            }
        }
    }
}