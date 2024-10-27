package com.poulastaa.mflix.core.presentation.designsystem.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import com.poulastaa.mflix.core.presentation.designsystem.model.ShapeCornerRadius

interface IndentAnimation {
    @Composable
    fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius,
    ): State<Shape>
}