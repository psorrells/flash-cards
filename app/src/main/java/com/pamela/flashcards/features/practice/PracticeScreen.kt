package com.pamela.flashcards.features.practice

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pamela.flashcards.R
import com.pamela.flashcards.model.EmptyResultError
import com.pamela.flashcards.model.GetNewCardException
import com.pamela.flashcards.ui.animateDpOnChange
import com.pamela.flashcards.ui.animateFloatOnChange
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.DefaultErrorMessage
import com.pamela.flashcards.ui.component.DeleteDialog
import com.pamela.flashcards.ui.component.StyledTopBar
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.FlashCardsTheme

@Composable
fun PracticeScreen(viewModel: PracticeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val screenEntryAnimation = animateFloatOnChange(startValue = 0.0F, animationSpec = tween(300))
    LaunchedEffect(key1 = Unit, block = { screenEntryAnimation.setValue(1.0F) })

    val displayHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardSlideAnimation = animateDpOnChange(startValue = 0.dp, animationSpec = tween(500, easing = EaseOut))
    LaunchedEffect(key1 = cardSlideAnimation.value == displayHeight) {
        if (cardSlideAnimation.value == displayHeight && uiState.errorState is GetNewCardException) {
            viewModel.setCurrentCardWithNextDueCard()
            cardSlideAnimation.setValue(0.dp)
        }
    }
    val onFinishCardFlipSlideDown = { float: Float ->
        if (float <= 10.0F && uiState.errorState is GetNewCardException) {
            cardSlideAnimation.setValue(displayHeight)
        }
    }


    Scaffold(
        modifier = Modifier
            .scaffoldDefaults()
            .clipToBounds(),
        topBar = {
            StyledTopBar(
                titleText = uiState.cardSet.name,
                onClickNavigation = viewModel::openNavDrawer,
                actions = {
                    IconButton(onClick = { viewModel.navigateToEditCard() }) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = stringResource(id = R.string.edit_card)
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = stringResource(id = R.string.delete_card)
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.isFlipped.not()) {
                BottomBarButtonFullWidth(
                    onClick = { viewModel.setIsFlipped(true) },
                    text = stringResource(id = R.string.flip),
                    enabled = (uiState.errorState == null)
                )
            } else {
                DifficultyButtonsRow(onClickDifficulty = viewModel::updateFlashCardWithDifficulty)
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
            Box(
                modifier = Modifier
                    .fillMaxSize(screenEntryAnimation.value)
                    .animateContentSize()
                    .absoluteOffset(y = cardSlideAnimation.value),
                contentAlignment = Alignment.Center
            ) {
                when (uiState.errorState) {
                    null, is GetNewCardException -> FlashCard(
                        card = uiState.currentCard,
                        isFlipped = uiState.isFlipped,
                        setIsFlipped = viewModel::setIsFlipped,
                        animationListener = onFinishCardFlipSlideDown
                    )

                    is EmptyResultError -> EmptyDeckDisplay(viewModel::navigateToAddCard)
                    else -> DefaultErrorMessage()
                }
            }
        }
        if (showDeleteDialog) {
            DeleteDialog(
                onCancel = { showDeleteDialog = false },
                onConfirm = { viewModel.deleteCurrentCard(); showDeleteDialog = false },
                isDeck = false
            )
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