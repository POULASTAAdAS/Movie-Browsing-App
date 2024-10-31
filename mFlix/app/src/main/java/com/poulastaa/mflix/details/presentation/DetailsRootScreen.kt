package com.poulastaa.mflix.details.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun DetailsRootScreen(
    viewModel: DetailsViewModel,
    windowSizeClass: WindowSizeClass,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            DetailsSmallScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = {

                },
                navigateBack = navigateBack
            )
        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}