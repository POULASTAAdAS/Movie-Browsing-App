package com.poulastaa.mflix.core.presentation.designsystem

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import com.poulastaa.mflix.core.presentation.designsystem.utils.animatedNavBarMeasurePolicy
import com.poulastaa.mflix.core.presentation.designsystem.model.BallAnimInfo
import com.poulastaa.mflix.core.presentation.designsystem.model.ShapeCornerRadius
import com.poulastaa.mflix.core.presentation.designsystem.repository.BallAnimation
import com.poulastaa.mflix.core.presentation.designsystem.repository.IndentAnimation
import com.poulastaa.mflix.core.presentation.designsystem.theme.ballSize
import com.poulastaa.mflix.core.presentation.designsystem.repository.Height
import com.poulastaa.mflix.core.presentation.designsystem.repository.Parabolic
import com.poulastaa.mflix.core.presentation.designsystem.utils.ballTransform
import com.poulastaa.mflix.core.presentation.designsystem.utils.shapeCornerRadius


@Composable
fun AnimatedNavigationBottomBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color,
    ballColor: Color,
    cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
    ballAnimation: BallAnimation = Parabolic(tween(300)),
    indentAnimation: IndentAnimation = Height(tween(300)),
    content: @Composable () -> Unit,
) {
    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }
    val measurePolicy = animatedNavBarMeasurePolicy {
        itemPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    val selectedItemOffset by remember(selectedIndex, itemPositions) {
        derivedStateOf {
            if (itemPositions.isNotEmpty()) itemPositions[selectedIndex] else Offset.Unspecified
        }
    }

    val indentShape = indentAnimation.animateIndentShapeAsState(
        shapeCornerRadius = cornerRadius,
        targetOffset = selectedItemOffset
    )

    val ballAnimInfoState = ballAnimation.animateAsState(
        targetOffset = selectedItemOffset,
    )

    Box(
        modifier = modifier
    ) {
        Layout(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = indentShape.value
                }
                .background(barColor),
            content = content,
            measurePolicy = measurePolicy
        )

        if (ballAnimInfoState.value.offset.isSpecified) {
            ColorBall(
                ballAnimInfo = ballAnimInfoState.value,
                ballColor = ballColor,
                sizeDp = ballSize
            )
        }
    }
}

@Composable
private fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    ballAnimInfo: BallAnimInfo,
    sizeDp: Dp,
) {
    Box(
        modifier = modifier
            .ballTransform(ballAnimInfo)
            .size(sizeDp)
            .clip(shape = CircleShape)
            .background(ballColor)
    )
}