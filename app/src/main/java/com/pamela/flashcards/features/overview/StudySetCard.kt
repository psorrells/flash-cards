package com.pamela.flashcards.features.overview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.ui.styles.getButtonStyles
import com.pamela.flashcards.util.getFormattedDate
import com.pamela.flashcards.util.getFormattedTime

@Composable
fun StudySetCard(
    cardSet: FlashCardSetDomain,
    onClickSet: (FlashCardSetDomain) -> Unit,
    onClickDelete: (FlashCardSetDomain) -> Unit,
    onClickEdit: (FlashCardSetDomain) -> Unit,
    onClickAddCard: (FlashCardSetDomain) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    var widthValue by remember { mutableFloatStateOf(0.0F) }
    val widthAnimation by animateFloatAsState(
        targetValue = widthValue,
        animationSpec = tween(1000, easing = EaseOutBounce),
        label = "widthAnimation"
    )
    LaunchedEffect(key1 = Unit) { widthValue = 1.0F }

    var expanded by remember { mutableStateOf(false) }
    val showContent by remember { derivedStateOf { widthAnimation > 0.75F } }
    LaunchedEffect(key1 = cardSet, block = { expanded = false })
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth(widthAnimation)
            .clickable { onClickSet(cardSet) }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (showContent) {
                IconButton(
                    onClick = { expanded = expanded.not() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                        contentDescription = stringResource(
                            id = if (expanded) R.string.show_less else R.string.show_more
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .animateContentSize()
                ) {
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = cardSet.name
                    )
                    if (expanded) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = stringResource(
                                id = R.string.cards_due,
                                cardSet.totalDue,
                                cardSet.size
                            )
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text =
                            if (cardSet.lastStudiedAt != null) {
                                stringResource(
                                    id = R.string.last_studied_time,
                                    cardSet.lastStudiedAt.getFormattedDate(),
                                    cardSet.lastStudiedAt.getFormattedTime()
                                )
                            } else {
                                stringResource(id = R.string.never)
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { onClickAddCard(cardSet) }
                            ) {
                                Text(text = stringResource(id = R.string.add_card))
                            }
                            TextButton(
                                onClick = { onClickEdit(cardSet) }
                            ) {
                                Text(text = stringResource(id = R.string.edit))
                            }
                            TextButton(
                                onClick = { showDeleteDialog = true },
                                colors = getButtonStyles().errorText
                            ) {
                                Text(text = stringResource(id = R.string.delete))
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDeleteDialog) {
        Dialog(onDismissRequest = { showDeleteDialog = false }) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_set_header),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.delete_set_body),
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onClickDelete(cardSet)
                            showDeleteDialog = false
                        },
                        colors = getButtonStyles().errorDefault
                    ) {
                        Text(text = stringResource(id = R.string.confirm_delete_set))
                    }
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text(text = stringResource(id = R.string.cancel_delete_set))
                    }
                }
            }
        }
    }
}