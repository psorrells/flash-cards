package com.pamela.flashcards.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
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

private val LightColorScheme = lightColorScheme(
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
fun MaterialTheme.colorPositive(): Color {
    return if (this.colorScheme.background == LightColorScheme.background) {
        Color(0xFF0E8A73)
    } else {
        Color(0xFF33CA7F)
    }
}

@Composable
fun FlashCardsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}