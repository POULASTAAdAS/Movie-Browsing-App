package com.poulastaa.mflix.core.presentation.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.poulastaa.mflix.core.presentation.designsystem.model.ShapeCornerRadius

fun shapeCornerRadius(cornerRadius: Float) = ShapeCornerRadius(
    topLeft = cornerRadius,
    topRight = cornerRadius,
    bottomRight = cornerRadius,
    bottomLeft = cornerRadius
)

@Composable
fun shapeCornerRadius(cornerRadius: Dp) = ShapeCornerRadius(
    topLeft = cornerRadius.toPxf(),
    topRight = cornerRadius.toPxf(),
    bottomRight = cornerRadius.toPxf(),
    bottomLeft = cornerRadius.toPxf()
)