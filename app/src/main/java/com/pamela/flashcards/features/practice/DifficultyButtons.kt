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

@Composable
fun DifficultyButton(
    onClick: () -> Unit,
    containerColor: Color,
    textColor: Color,
    text: String
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = text,
            color = textColor
        )
    }
}

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
        DifficultyButton(
            onClick = { onClickDifficulty(Difficulty.EASY) },
            containerColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.easy)
        )
        DifficultyButton(
            onClick = { onClickDifficulty(Difficulty.MEDIUM) },
            containerColor = MaterialTheme.colorScheme.secondary,
            textColor = MaterialTheme.colorScheme.onSecondary,
            text = stringResource(id = R.string.medium)
        )
        DifficultyButton(
            onClick = { onClickDifficulty(Difficulty.HARD) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            textColor = MaterialTheme.colorScheme.onTertiary,
            text = stringResource(id = R.string.hard)
        )
        DifficultyButton(
            onClick = { onClickDifficulty(Difficulty.AGAIN) },
            containerColor = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onError,
            text = stringResource(id = R.string.again)
        )
    }
}