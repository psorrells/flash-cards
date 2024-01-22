package com.pamela.flashcards.features.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.AlertText
import com.pamela.flashcards.ui.component.StyledButton

@Composable
fun EmptyDeckDisplay(onClickAddCard: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Rounded.Done,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(0.4F),
            tint = MaterialTheme.colorScheme.primary
        )
        AlertText(
            text = stringResource(id = R.string.no_cards),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledButton(onClick = onClickAddCard, text = stringResource(id = R.string.add_card))
    }
}