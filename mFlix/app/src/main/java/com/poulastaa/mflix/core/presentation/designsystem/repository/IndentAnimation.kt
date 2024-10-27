package com.poulastaa.mflix.core.presentation.designsystem.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import com.poulastaa.mflix.core.presentation.designsystem.model.ShapeCornerRadius
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Shape

interface IndentAnimation {
    @Composable
    fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius
    ): State<Shape>
}