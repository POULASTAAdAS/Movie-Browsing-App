package com.poulastaa.mflix.core.presentation.designsystem.model

import com.poulastaa.mflix.core.presentation.designsystem.utils.shapeCornerRadius

data class IndentShapeData(
    val xIndent: Float = 0f,
    val height: Float = 0f,
    val width: Float = 0f,
    val cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
    val ballOffset: Float = 0f,
)