package com.pamela.flashcards.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    background = Color(0xFF25291C),
    onBackground = Color(0xFFFBF9EF),
    surface = Color(0xFF25291C),
    primary = Color(0xFF036170),
    onPrimary = Color(0xFFFBF9EF),
    secondary = Color(0xFF0E848A),
    onSecondary = Color(0xFFFBF9EF),
    tertiary = Color(0xFFBE9754),
    onTertiary = Color(0xFFFBF9EF),
    error = Color(0xFFC03816),
    onError = Color(0xFFFBF9EF),
    errorContainer = Color(0xFF5D1212),
    onErrorContainer = Color(0xFFFC9F5B),
    primaryContainer = Color(0xFF6C762E),
    onPrimaryContainer = Color(0xFFFBF9EF),
    secondaryContainer = Color(0xFF2D7660),
    onSecondaryContainer = Color(0xFFFBF9EF),
    tertiaryContainer = Color(0xFF835520),
    onTertiaryContainer = Color(0xFFFBF9EF),
    surfaceVariant = Color(0xFF424932),
    onSurfaceVariant = Color(0xFFFBF9EF),
    surfaceTint = Color(0xFF33CA7F),
)
val LightColorScheme = lightColorScheme(
    background = Color(0xFFFBF9EF),
    onBackground = Color(0xFF0A3055),
    surface = Color(0xFFFBF9EF),
    primary = Color(0xFF406286),
    onPrimary = Color(0xFFFBF9EF),
    secondary = Color(0xFF0E848A),
    onSecondary = Color(0xFFFBF9EF),
    tertiary = Color(0xFFBE9754),
    onTertiary = Color(0xFFFBF9EF),
    error = Color(0xFFC03816),
    onError = Color(0xFFFBF9EF),
    errorContainer = Color(0xFFFC9F5B),
    onErrorContainer = Color(0xFF5D1212),
    primaryContainer = Color(0xFFE2E7C1),
    onPrimaryContainer = Color(0xFF0A3055),
    secondaryContainer = Color(0xFF7DCFB6),
    onSecondaryContainer = Color(0xFF0A3055),
    tertiaryContainer = Color(0xFFF0DAC1),
    onTertiaryContainer = Color(0xFF0A3055),
    surfaceVariant = Color(0xFFEEEAD0),
    onSurfaceVariant = Color(0xFF34385e),
    surfaceTint = Color(0xFF33CA7F),
)

@Composable
fun MaterialTheme.colorTextPositive(): Color {
    return if (colorScheme.background == LightColorScheme.background) {
        Color(0xFF0E8A73)
    } else {
        Color(0xFF33CA7F)
    }
}

@Composable
fun MaterialTheme.colorTextNegative(): Color {
    return if (colorScheme.background == LightColorScheme.background) {
        Color(0xFFC03816)
    } else {
        Color(0xFFFC9F5B)
    }
}

@Composable
fun MaterialTheme.colorTextNeutral(): Color {
    return if (colorScheme.background == LightColorScheme.background) {
        Color(0xFF406286)
    } else {
        Color(0xFF7DCFB6)
    }
}