package com.poulastaa.mflix.home.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.HomeTopBar
import com.poulastaa.mflix.home.presentation.components.PrevItemCard
import com.poulastaa.mflix.home.presentation.components.SpotlightCard
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSmallScreen(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HomeTopBar(state, scroll) {

            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scroll.nestedScrollConnection),
        ) {
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
                Column(
                    modifier = Modifier.height(500.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SpotlightCard(
                        modifier = Modifier
                            .fillMaxWidth(.85f)
                            .fillMaxSize(),
                        title = state.spotLight.title,
                        description = state.spotLight.description,
                        rating = state.spotLight.rating,
                        coverImage = state.spotLight.imageUrl,
                        isInFavourite = state.spotLight.isInFavourite,
                        onFavouriteClick = {

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

            heading(R.string.popular)

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                ItemList(state.popularList) { id, tyep ->

                }
            }

            heading(R.string.top_rated)

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                ItemList(state.topRated) { id, tyep ->

                }
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(Modifier.height(MaterialTheme.dimens.large1))
            }

            heading(R.string.explore)

            // todo paging list
        }
    }
}


private fun LazyGridScope.heading(
    @StringRes id: Int
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium1)
        ) {
            Text(
                text = stringResource(id),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.medium1))
    }
}

@Composable
private fun ItemList(
    list: List<UiPrevItem>,
    onClick: (id: Long, type: UiPrevItemType) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
    ) {
        items(list) { item ->
            PrevItemCard(
                modifier = Modifier
                    .width(150.dp)
                    .padding(MaterialTheme.dimens.small2),
                item = item,
                onClick = {
                    onClick(item.id, item.type)
                }
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
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
                onAction = {}
            )
        }
    }
}