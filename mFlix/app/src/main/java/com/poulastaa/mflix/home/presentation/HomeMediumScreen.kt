package com.poulastaa.mflix.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.HomeFilterChip
import com.poulastaa.mflix.home.presentation.components.HomeMediumLoadingScreen
import com.poulastaa.mflix.home.presentation.components.homeCommonContent
import com.poulastaa.mflix.home.presentation.components.spotlightMediumCard
import kotlinx.coroutines.flow.flowOf
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMediumScreen(
    state: HomeUiState,
    more: LazyPagingItems<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var cardHeight by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                cardHeight = (it.size.height / 2.9).roundToInt()
            },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.dataLoaded, label = "home loading animation") {
            when (it) {
                true -> LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .nestedScroll(scroll.nestedScrollConnection),
                ) {
                    spotlightMediumCard(
                        cardHeight = cardHeight,
                        userName = state.user.name,
                        spotlight = state.spotLight,
                        onAction = onAction,
                        scroll = scroll
                    )

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.medium1))
                    }

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

                    homeCommonContent(state, onAction, more)
                }

                false -> HomeMediumLoadingScreen(paddingValues, cardHeight)
            }
        }
    }
}


@Preview(
    widthDp = 700,
    heightDp = 1280,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    widthDp = 700,
    heightDp = 1280,
)
@Composable
private fun Preview() {
    val dummyMoreItems = flowOf(
        PagingData.from(
            listOf<UiPrevItem>()
        )
    )

    PrevThem {
        Surface {
            HomeMediumScreen(
                state = HomeUiState(
                    spotLight = UiPrevItem(
                        id = 1,
                        title = "",
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