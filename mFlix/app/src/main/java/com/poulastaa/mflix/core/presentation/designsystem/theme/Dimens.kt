package com.poulastaa.mflix.core.presentation.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val small1: Dp = 4.dp,
    val small2: Dp = 6.dp,
    val small3: Dp = 8.dp,
    val medium1: Dp = 16.dp,
    val medium2: Dp = 18.dp,
    val medium3: Dp = 24.dp,
    val large1: Dp = 32.dp,
    val large2: Dp = 52.dp,
)

internal val CompactSmallDimens = Dimens(
    small1 = 2.dp,
    small2 = 4.dp,
    small3 = 6.dp,
    medium1 = 10.dp,
    medium2 = 14.dp,
    medium3 = 20.dp,
)

internal val CompactMediumDimens = Dimens( // default
    small1 = 4.dp,
    small2 = 6.dp,
    small3 = 8.dp,
    medium1 = 16.dp,
    medium2 = 18.dp,
    medium3 = 24.dp,
    large1 = 32.dp,
    large2 = 52.dp
)


internal val CompactDimens = Dimens(
    small1 = 4.dp,
    small2 = 6.dp,
    small3 = 8.dp,
    medium1 = 16.dp,
    medium2 = 18.dp,
    medium3 = 24.dp,
)


internal val MediumDimens = Dimens(
    small1 = 8.dp,
    small2 = 12.dp,
    small3 = 18.dp,
    medium1 = 24.dp,
    medium2 = 28.dp,
    medium3 = 32.dp,
    large1 = 36.dp,
    large2 = 42.dp
)

internal val ExpandedDimens = Dimens(
    small1 = 4.dp,
    small2 = 8.dp,
    small3 = 16.dp,
    medium1 = 20.dp,
    medium2 = 24.dp,
    medium3 = 28.dp,
)