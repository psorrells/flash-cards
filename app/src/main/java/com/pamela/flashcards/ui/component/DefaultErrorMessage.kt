package com.pamela.flashcards.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.pamela.flashcards.R

@Composable
fun DefaultErrorMessage() {
    AlertText(
        text = stringResource(id = R.string.default_error_text),
        color = MaterialTheme.colorScheme.error
    )
}
