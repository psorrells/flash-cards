package com.pamela.flashcards.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


fun Modifier.scaffoldDefaults() = composed {
    fillMaxSize()
    safeDrawingPadding()
}