package com.poulastaa.mflix.home.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.presentation.designsystem.AppScreenWindowSize

@Composable
fun HomeRootScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            HomeSmallScreen(
                state = state,
                onAction = viewModel::onAction
            )
        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}