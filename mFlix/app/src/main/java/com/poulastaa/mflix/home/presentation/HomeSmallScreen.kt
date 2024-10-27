package com.poulastaa.mflix.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.HomeFilterChip
import com.poulastaa.mflix.home.presentation.components.HomeSmallLoadingScreen
import com.poulastaa.mflix.home.presentation.components.SmallHomeTopBar
import com.poulastaa.mflix.home.presentation.components.SpotlightSmallCard
import com.poulastaa.mflix.home.presentation.components.homeCommonContent
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSmallScreen(
    state: HomeUiState,
    more: LazyPagingItems<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            SmallHomeTopBar(state, scroll) {
                onAction(HomeUiAction.OnSearchClick)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.dataLoaded, label = "home loading animation") {
            when (it) {
                true -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .nestedScroll(scroll.nestedScrollConnection),
                    contentPadding = WindowInsets.systemBars.asPaddingValues()
                ) {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        HomeFilterChip(
                            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium1),
                            filterType = state.filterType,
                            onClick = { filterType ->
                                onAction(HomeUiAction.OnFilterTypeChange(filterType))
                            }
                        )
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.medium3))
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Column(
                            modifier = Modifier.height(500.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SpotlightSmallCard(
                                modifier = Modifier
                                    .fillMaxWidth(.85f)
                                    .fillMaxSize(),
                                title = state.spotLight.title,
                                description = state.spotLight.description,
                                rating = state.spotLight.rating,
                                coverImage = state.spotLight.imageUrl,
                                isInFavourite = state.spotLight.isInFavourite,
                                onFavouriteClick = {
                                    onAction(HomeUiAction.OnSpotlightFavouriteClick)
                                },
                                onCardClick = {
                                    onAction(
                                        HomeUiAction.OnItemClick(
                                            id = state.spotLight.id,
                                            type = state.spotLight.type
                                        )
                                    )
                                }
                            )
                        }
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.small3))
                    }

                    homeCommonContent(state, onAction, more)
                }

                false -> HomeSmallLoadingScreen(paddingValues)
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    val dummyMoreItems = flowOf(
        PagingData.from(
            listOf<UiPrevItem>()
        )
    )

    PrevThem {
        Surface {
            HomeSmallScreen(
                state = HomeUiState(
                    spotLight = UiPrevItem(
                        id = 1,
                        title = "That Cool Movie",
                        description = "This is a cool movie about a boy and a girl who are in love",
                        rating = 4.5,
                        isInFavourite = true,
                        imageUrl = "",
                    ),
                    popularList = (1..10).map {
                        UiPrevItem(
                            id = it.toLong(),
                            title = "That Cool Movie $it",
                            description = "This is a cool movie description $it",
                            rating = Random.nextInt(1, 10).toDouble(),
                            isInFavourite = true,
                            imageUrl = "",
                        )
                    },
                    topRated = (1..10).map {
                        UiPrevItem(
                            id = it.toLong(),
                            title = "That Cool Movie $it",
                            description = "This is a cool movie description $it",
                            rating = Random.nextInt(1, 10).toDouble(),
                            isInFavourite = true,
                            imageUrl = "",
                        )
                    }
                ),
                more = dummyMoreItems.collectAsLazyPagingItems(),
                onAction = {}
            )
        }
    }
}