package com.pamela.flashcards.ui.styles

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pamela.flashcards.ui.theme.colorTextNegative
import com.pamela.flashcards.ui.theme.colorTextNeutral
import com.pamela.flashcards.ui.theme.colorTextPositive

interface ButtonStyles {
    val errorDefault: ButtonColors
    val errorText: ButtonColors
    val positiveText: ButtonColors
    val neutralText: ButtonColors
}


@Composable
fun getButtonStyles(): ButtonStyles {
    return object : ButtonStyles {
        override val errorDefault =
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        override val errorText =
            ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorTextNegative())
        override val positiveText =
            ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorTextPositive())
        override val neutralText =
            ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorTextNeutral())
    }
}