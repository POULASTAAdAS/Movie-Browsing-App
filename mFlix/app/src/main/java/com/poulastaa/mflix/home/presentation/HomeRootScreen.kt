package com.poulastaa.mflix.home.presentation

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun HomeRootScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current as Activity
    val state by viewModel.state.collectAsStateWithLifecycle()

    val newOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    context.requestedOrientation = newOrientation

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            HomeSmallScreen(
                state = state,
                more = viewModel.more.collectAsLazyPagingItems(),
                onAction = viewModel::onAction
            )
        },
        mediumContent = {
            HomeMediumScreen(
                state = state,
                more = viewModel.more.collectAsLazyPagingItems(),
                onAction = viewModel::onAction
            )
        },
        expandedContent = {
            // todo expand home content
        }
    )
}