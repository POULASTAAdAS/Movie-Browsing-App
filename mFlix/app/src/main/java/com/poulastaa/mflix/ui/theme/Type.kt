package com.poulastaa.mflix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.poulastaa.mflix.R

val Roboto = FontFamily(
    Font(
        resId = R.font.roboto_light,
        weight = FontWeight.ExtraLight
    ),
    Font(
        resId = R.font.roboto_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.roboto_black,
        weight = FontWeight.Black
    )
)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = Roboto),
    displayMedium = baseline.displayMedium.copy(fontFamily = Roboto),
    displaySmall = baseline.displaySmall.copy(fontFamily = Roboto),

    headlineLarge = baseline.headlineLarge.copy(fontFamily = Roboto),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = Roboto),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = Roboto),

    titleLarge = baseline.titleLarge.copy(fontFamily = Roboto),
    titleMedium = baseline.titleMedium.copy(fontFamily = Roboto),
    titleSmall = baseline.titleSmall.copy(fontFamily = Roboto),

    bodyLarge = baseline.bodyLarge.copy(fontFamily = Roboto),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = Roboto),
    bodySmall = baseline.bodySmall.copy(fontFamily = Roboto),

    labelLarge = baseline.labelLarge.copy(fontFamily = Roboto),
    labelMedium = baseline.labelMedium.copy(fontFamily = Roboto),
    labelSmall = baseline.labelSmall.copy(fontFamily = Roboto),
)