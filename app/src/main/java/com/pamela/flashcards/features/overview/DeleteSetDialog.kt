package com.pamela.flashcards.features.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.StyledButton
import com.pamela.flashcards.ui.component.StyledTextButton
import com.pamela.flashcards.ui.styles.getButtonStyles

@Composable
fun DeleteSetDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.delete_set_header),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.delete_set_body),
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(16.dp))
                StyledButton(
                    onClick = onConfirm,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    text = stringResource(id = R.string.confirm_delete_set)
                )
                StyledTextButton(
                    onClick = onCancel,
                    text = stringResource(id = R.string.cancel_delete_set)
                )
            }
        }
    }
}