package com.pamela.flashcards.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.pamela.flashcards.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DbFailureBottomSheet() {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    if (!showBottomSheet) return

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { showBottomSheet = false }
    ) {
        AlertText(
            text = stringResource(id = R.string.database_error_text),
            color = MaterialTheme.colorScheme.error
        )
    }
}