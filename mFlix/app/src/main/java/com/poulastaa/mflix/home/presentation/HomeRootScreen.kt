package com.poulastaa.mflix.home.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun HomeRootScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            HomeExpandedScreen(
                state = state,
                more = viewModel.more.collectAsLazyPagingItems(),
                onAction = viewModel::onAction
            )
        }
    )
}