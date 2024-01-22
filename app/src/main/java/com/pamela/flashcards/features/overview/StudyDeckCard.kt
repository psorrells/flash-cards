package com.pamela.flashcards.features.overview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutCirc
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R
import com.pamela.flashcards.model.FlashCardDeckDomain
import com.pamela.flashcards.ui.component.StyledTextButton
import com.pamela.flashcards.ui.styles.getButtonStyles
import com.pamela.flashcards.util.getFormattedDate
import com.pamela.flashcards.util.getFormattedTime

@Composable
fun StudyDeckCard(
    cardDeck: FlashCardDeckDomain,
    onClickDeck: (FlashCardDeckDomain) -> Unit,
    onClickDelete: (FlashCardDeckDomain) -> Unit,
    onClickEdit: (FlashCardDeckDomain) -> Unit,
    onClickAddCard: (FlashCardDeckDomain) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    var widthValue by remember { mutableFloatStateOf(0.0F) }
    LaunchedEffect(key1 = Unit) { widthValue = 1.0F }

    var expanded by remember { mutableStateOf(false) }
    val showContent by remember { derivedStateOf { widthValue > 0.75F } }
    LaunchedEffect(key1 = cardDeck, block = { expanded = false })
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .clickable { onClickDeck(cardDeck) }
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(widthValue)
                .animateContentSize(animationSpec = tween(300, easing = EaseOutCirc))
        ) {
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
                        .composed {
                            if (expanded) padding(top = 14.dp, start = 14.dp, end = 14.dp)
                            else padding(14.dp)
                        }
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = cardDeck.name
                    )
                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = stringResource(
                                id = R.string.cards_due,
                                cardDeck.totalDue,
                                cardDeck.size
                            )
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text =
                            if (cardDeck.lastStudiedAt != null) {
                                stringResource(
                                    id = R.string.last_studied_time,
                                    cardDeck.lastStudiedAt.getFormattedDate(),
                                    cardDeck.lastStudiedAt.getFormattedTime()
                                )
                            } else {
                                stringResource(id = R.string.never)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            StyledTextButton(
                                onClick = { onClickAddCard(cardDeck) },
                                text = stringResource(id = R.string.add_card),
                                colors = getButtonStyles().positiveText
                            )
                            StyledTextButton(
                                onClick = { onClickEdit(cardDeck) },
                                text = stringResource(id = R.string.edit)
                            )
                            StyledTextButton(
                                onClick = { showDeleteDialog = true },
                                text = stringResource(id = R.string.delete),
                                colors = getButtonStyles().errorText
                            )
                        }
                    }
                }
            }
        }
    }
    if (showDeleteDialog) {
        DeleteDeckDialog(
            onCancel = { showDeleteDialog = false },
            onConfirm = { onClickDelete(cardDeck); showDeleteDialog = false }
        )
    }
}


