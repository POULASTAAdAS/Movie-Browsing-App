package com.poulastaa.mflix.core.presentation.designsystem.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.repository.ColorButtonAnimation
import com.poulastaa.mflix.core.presentation.ui.BellColorButton

@Stable
data class WiggleButtonItem(
    @DrawableRes val backgroundIcon: Int,
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
)