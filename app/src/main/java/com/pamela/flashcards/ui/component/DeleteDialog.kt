package com.pamela.flashcards.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.styles.getButtonStyles

@Composable
fun DeleteDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    isDeck: Boolean
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
                    text = stringResource(id = R.string.delete_dialog_header),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = if (isDeck) R.string.delete_deck_body else R.string.delete_card_body),
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(16.dp))
                StyledButton(
                    onClick = onConfirm,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    text = stringResource(id = if (isDeck) R.string.confirm_delete_deck else R.string.confirm_delete_card)
                )
                StyledTextButton(
                    onClick = onCancel,
                    text = stringResource(id = if (isDeck) R.string.cancel_delete_deck else R.string.cancel_delete_card),
                    colors = getButtonStyles().neutralText
                )
            }
        }
    }
}