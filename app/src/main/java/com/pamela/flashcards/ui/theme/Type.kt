package com.pamela.flashcards.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.pamela.flashcards.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val googleFont = GoogleFont("Overpass")

val newFontFamily = FontFamily(fonts = listOf(Font(googleFont = googleFont, fontProvider = provider)))

// Set of Material typography styles to start with
val oldTypography = Typography()
val Typography = Typography(
    displayLarge = oldTypography.displayLarge.copy(fontFamily = newFontFamily),
displayMedium = oldTypography.displayMedium.copy(fontFamily = newFontFamily),
displaySmall = oldTypography.displaySmall.copy(fontFamily = newFontFamily),
headlineLarge = oldTypography.headlineLarge.copy(fontFamily = newFontFamily),
headlineMedium = oldTypography.headlineMedium.copy(fontFamily = newFontFamily),
headlineSmall = oldTypography.headlineSmall.copy(fontFamily = newFontFamily),
titleLarge = oldTypography.titleLarge.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold),
titleMedium = oldTypography.titleMedium.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold),
titleSmall = oldTypography.titleSmall.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold),
bodyLarge = oldTypography.bodyLarge.copy(fontFamily = newFontFamily),
bodyMedium = oldTypography.bodyMedium.copy(fontFamily = newFontFamily),
bodySmall = oldTypography.bodySmall.copy(fontFamily = newFontFamily),
labelLarge = oldTypography.labelLarge.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold),
labelMedium = oldTypography.labelMedium.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold),
labelSmall = oldTypography.labelSmall.copy(fontFamily = newFontFamily, fontWeight = FontWeight.SemiBold)
)