package com.pamela.flashcards.features.practice

import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.DefaultErrorMessage
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.FlashCardsTheme

@Composable
fun PracticeScreen(viewModel: PracticeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val displayHeight = LocalConfiguration.current.screenHeightDp.dp
    var yValue by remember { mutableStateOf(0.dp) }
    val onFinishCardFlipAnimHideCard = { float: Float ->
        if (float <= 10.0F && uiState.errorState is GetNewCardException) {
            yValue = displayHeight
        }
    }
    val slideDown by animateDpAsState(
        targetValue = yValue,
        animationSpec = tween(700, easing = EaseOutBounce),
        label = "flashCardRotate",
        finishedListener = {
            if (it == displayHeight && uiState.errorState is GetNewCardException) {
                viewModel.setCurrentCardWithNextDueCard()
                yValue = 0.dp
            }
        }
    )

    Scaffold(
        modifier = Modifier
            .scaffoldDefaults()
            .clipToBounds(),
        topBar = { TopBarHeader(titleText = uiState.cardSet.name) },
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
                .fillMaxSize()
                .absoluteOffset(y = slideDown),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.errorState) {
                null, is GetNewCardException -> FlashCard(
                    card = uiState.currentCard,
                    isFlipped = uiState.isFlipped,
                    setIsFlipped = viewModel::setIsFlipped,
                    animationListener = onFinishCardFlipAnimHideCard
                )
                is EmptyResultError -> EmptyDeckDisplay(viewModel::navigateToAddCard)
                else -> DefaultErrorMessage()
            }
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