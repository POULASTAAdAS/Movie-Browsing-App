package com.poulastaa.mflix.core.presentation.designsystem.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.DpOffset

data class ButtonBackground(
    @DrawableRes val icon: Int,
    val offset: DpOffset = DpOffset.Zero,
)
