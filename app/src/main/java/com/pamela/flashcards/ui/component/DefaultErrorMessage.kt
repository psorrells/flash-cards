package com.pamela.flashcards.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pamela.flashcards.R

@Composable
fun DefaultErrorMessage() {
    AlertText(
        text = stringResource(id = R.string.default_error),
        color = MaterialTheme.colorScheme.error
    )
}
