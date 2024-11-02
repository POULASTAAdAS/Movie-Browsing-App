package com.poulastaa.mflix.details.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun DetailsRootScreen(
    viewModel: DetailsViewModel,
    windowSizeClass: WindowSizeClass,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val config = LocalConfiguration.current

    ObserveAsEvent(viewModel.uiState) {
        when (it) {
            is DetailsUiEvent.NavigateToDetails -> navigateToDetails(it.id, it.type)
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            DetailsSmallScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        mediumContent = {
            DetailsMediumScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        expandedContent = {
            if (config.screenWidthDp > 980) DetailsExpandedScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            ) else DetailsSmallExpandedScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        }
    )
}