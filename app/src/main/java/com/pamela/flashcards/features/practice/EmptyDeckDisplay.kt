package com.pamela.flashcards.features.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pamela.flashcards.R

@Composable
fun EmptyDeckDisplay(onClickAddCard: () -> Unit) {
    Column {
        Text(stringResource(id = R.string.no_cards))
        Button(onClick = onClickAddCard) { Text(text = stringResource(id = R.string.add_card)) }
    }
}