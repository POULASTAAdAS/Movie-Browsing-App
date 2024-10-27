package com.poulastaa.mflix.core.presentation.designsystem.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import com.poulastaa.mflix.core.presentation.designsystem.model.BallAnimInfo

interface BallAnimation {
    @Composable
    fun animateAsState(targetOffset: Offset): State<BallAnimInfo>
}