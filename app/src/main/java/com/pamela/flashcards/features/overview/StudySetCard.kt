package com.pamela.flashcards.features.overview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R
import com.pamela.flashcards.model.FlashCardSetDomain
import com.pamela.flashcards.ui.component.StyledTextButton
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
    LaunchedEffect(key1 = Unit) { widthValue = 1.0F }

    var expanded by remember { mutableStateOf(false) }
    val showContent by remember { derivedStateOf { widthValue > 0.75F } }
    LaunchedEffect(key1 = cardSet, block = { expanded = false })
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .clickable { onClickSet(cardSet) }
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
                        .padding(14.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = cardSet.name
                    )
                    if (expanded) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            style = MaterialTheme.typography.labelLarge,
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
                            StyledTextButton(
                                onClick = { onClickAddCard(cardSet) },
                                text = stringResource(id = R.string.add_card),
                                colors = getButtonStyles().positiveText
                            )
                            StyledTextButton(
                                onClick = { onClickEdit(cardSet) },
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
        DeleteSetDialog(
            onCancel = { showDeleteDialog = false },
            onConfirm = { onClickDelete(cardSet); showDeleteDialog = false }
        )
    }
}


