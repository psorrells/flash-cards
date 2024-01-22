package com.pamela.flashcards.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.styles.getButtonStyles

@Composable
fun BottomBarButtonFullWidth(onClick: () -> Unit, text: String, icon: ImageVector? = null, enabled: Boolean = true) {
    Box(modifier = Modifier.padding(14.dp)) {
        Button(
            enabled = enabled,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = text,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun StyledButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = text
        )
    }
}

@Composable
fun StyledTextButton(
    onClick: () -> Unit,
    text: String,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    fullWidth: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = colors
    ) {
        Text(
            text = text,
            modifier = if (fullWidth) Modifier.fillMaxWidth() else Modifier,
            textAlign = textAlign
        )
    }
}