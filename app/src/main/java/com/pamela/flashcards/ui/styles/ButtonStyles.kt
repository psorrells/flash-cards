package com.pamela.flashcards.ui.styles

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

interface ButtonStyles {
    val errorDefault: ButtonColors
    val errorText: ButtonColors
}


@Composable
fun getButtonStyles(): ButtonStyles {
    return object : ButtonStyles {
        override val errorDefault =
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        override val errorText =
            ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
    }
}