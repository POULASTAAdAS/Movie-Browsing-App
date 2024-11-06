package com.poulastaa.mflix.search.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun SearchRootScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: SearchViewModel,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val data = viewModel.data.collectAsLazyPagingItems()

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            SearchCompactScreen(
                state = state,
                data = data,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}