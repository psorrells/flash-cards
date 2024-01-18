package com.pamela.flashcards.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBarHeader(titleText: String) {
    Column {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = titleText,
            modifier = Modifier.padding(14.dp)
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}