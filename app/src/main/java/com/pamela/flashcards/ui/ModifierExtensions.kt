package com.pamela.flashcards.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager


fun Modifier.scaffoldDefaults() = composed {
    val focusManager = LocalFocusManager.current
    fillMaxSize()
    safeDrawingPadding()
    clickable { focusManager.clearFocus() }
}