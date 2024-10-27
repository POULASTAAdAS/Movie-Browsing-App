package com.poulastaa.mflix.core.presentation.designsystem.repository

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.poulastaa.mflix.core.presentation.designsystem.model.ButtonBackground

@Stable
abstract class ColorButtonAnimation(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(10000),
    open val background: ButtonBackground,
) {
    @Composable
    abstract fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    )
}