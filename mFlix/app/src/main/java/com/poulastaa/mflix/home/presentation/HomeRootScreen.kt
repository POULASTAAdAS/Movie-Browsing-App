package com.poulastaa.mflix.home.presentation

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun HomeRootScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: HomeViewModel,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateToSearch: (type: AppScreen.SearchType) -> Unit,
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is HomeUiEvent.EmitToast -> Toast.makeText(
                context,
                event.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            is HomeUiEvent.NavigateToDetails -> navigateToDetails(event.id, event.type)

            HomeUiEvent.NavigateToSearch -> {
                val searchType = when (state.filterType) {
                    UiHomeFilterType.ALL -> AppScreen.SearchType.ALL
                    UiHomeFilterType.MOVIE -> AppScreen.SearchType.MOVIE
                    UiHomeFilterType.TV -> AppScreen.SearchType.TV_SHOW
                }

                navigateToSearch(searchType)
            }
        }
    }

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
            if (config.screenWidthDp > 980) HomeExpandedScreen(
                state = state,
                more = viewModel.more.collectAsLazyPagingItems(),
                onAction = viewModel::onAction
            ) else HomeExpandedSmallScreen(
                state = state,
                more = viewModel.more.collectAsLazyPagingItems(),
                onAction = viewModel::onAction
            )
        }
    )
}