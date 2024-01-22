package com.pamela.flashcards.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextOnlyErrorBottomSheet(text: String) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    if (!showBottomSheet) return

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { showBottomSheet = false }
    ) {
        AlertText(
            text = text,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}