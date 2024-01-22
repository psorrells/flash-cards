package com.pamela.flashcards.features.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.ui.component.StyledButton

@Composable
fun DifficultyButtonsRow(onClickDifficulty: (Difficulty) -> Unit) {
    Row(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterHorizontally
        )
    ) {
        StyledButton(
            onClick = { onClickDifficulty(Difficulty.EASY) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.easy)
        )
        StyledButton(
            onClick = { onClickDifficulty(Difficulty.MEDIUM) },
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            text = stringResource(id = R.string.medium)
        )
        StyledButton(
            onClick = { onClickDifficulty(Difficulty.HARD) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            text = stringResource(id = R.string.hard)
        )
        StyledButton(
            onClick = { onClickDifficulty(Difficulty.AGAIN) },
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
            text = stringResource(id = R.string.again)
        )
    }
}