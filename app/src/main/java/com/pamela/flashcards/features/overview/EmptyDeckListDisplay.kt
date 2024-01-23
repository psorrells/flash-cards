package com.pamela.flashcards.features.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.AlertText
import com.pamela.flashcards.ui.theme.colorTextNeutral

@Composable
fun EmptyDeckListDisplay() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(0.4F),
            tint = MaterialTheme.colorTextNeutral()
        )
        AlertText(
            text = stringResource(id = R.string.no_decks),
            color = MaterialTheme.colorTextNeutral()
        )
    }
}

@Preview
@Composable
fun PreviewEmptyDisplay() {
    Column(Modifier.fillMaxSize()) {
        EmptyDeckListDisplay()
    }
}