package com.poulastaa.mflix.core.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import com.poulastaa.mflix.core.presentation.designsystem.model.ButtonBackground
import com.poulastaa.mflix.core.presentation.designsystem.repository.ColorButtonAnimation
import com.poulastaa.mflix.core.presentation.designsystem.utils.rotationWithTopCenterAnchor
import kotlin.math.PI
import kotlin.math.sin

class BellColorButton(
    override val animationSpec: FiniteAnimationSpec<Float> = tween(),
    override val background: ButtonBackground,
    private val maxDegrees: Float = 30f,
) : ColorButtonAnimation(animationSpec, background) {
    @Composable
    override fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    ) {
        val rotationFraction = animateFloatAsState(
            targetValue = if (isSelected) 1f else 0f,
            animationSpec = animationSpec,
            label = "rotationFractionAnimation"
        )

        val color = animateColorAsState(
            targetValue = if (isSelected) Color.Black else LightGray,
            label = "colorAnimation"
        )

        Icon(
            modifier = modifier
                .rotationWithTopCenterAnchor(
                    if (isSelected) degreesRotationInterpolation(
                        maxDegrees,
                        rotationFraction.value
                    ) else 0f
                ),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color.value
        )
    }

    private fun degreesRotationInterpolation(maxDegrees: Float, fraction: Float) =
        sin(fraction * 2 * PI).toFloat() * maxDegrees
}