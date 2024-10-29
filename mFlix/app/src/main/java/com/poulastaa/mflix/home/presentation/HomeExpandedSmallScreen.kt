package com.poulastaa.mflix.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteEmptyIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteFillIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.ExpandedFilterChip
import com.poulastaa.mflix.home.presentation.components.ExpandedHeading
import com.poulastaa.mflix.home.presentation.components.ExpandedSpotlight
import com.poulastaa.mflix.home.presentation.components.ExpandedTopBar
import com.poulastaa.mflix.home.presentation.components.HomeLoadingScreen
import com.poulastaa.mflix.home.presentation.components.SpotlightSideCard
import com.poulastaa.mflix.home.presentation.components.expandedMore
import com.poulastaa.mflix.home.presentation.components.expandedPopular
import com.poulastaa.mflix.home.presentation.components.expandedTopRated
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeExpandedSmallScreen(
    state: HomeUiState,
    more: LazyPagingItems<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val cardHeight = (LocalConfiguration.current.screenHeightDp - 20).dp

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.dataLoaded, label = "home loading animation") {
            when (it) {
                true -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(6),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .nestedScroll(scroll.nestedScrollConnection),
                        ) {
                            item(
                                span = {
                                    GridItemSpan(3)
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(cardHeight)
                                ) {
                                    ExpandedSpotlight(state)

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(.28f)
                                            .align(Alignment.BottomCenter)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        MaterialTheme.colorScheme.background,
                                                    )
                                                )
                                            )
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        MaterialTheme.colorScheme.background,
                                                    )
                                                )
                                            )
                                            .padding(top = MaterialTheme.dimens.medium3)
                                            .padding(horizontal = MaterialTheme.dimens.medium1),
                                        verticalArrangement = Arrangement.Bottom
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = state.spotLight.title,
                                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                                fontWeight = FontWeight.Black,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )

                                            Text( // todo change to icons
                                                text = "Rating: ${state.spotLight.rating}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                            )
                                        }

                                        Spacer(Modifier.height(MaterialTheme.dimens.small1))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = state.spotLight.description,
                                                maxLines = 3,
                                                overflow = TextOverflow.Ellipsis,
                                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                modifier = Modifier.fillMaxWidth(.8f)
                                            )

                                            Spacer(Modifier.weight(1f))

                                            IconButton(
                                                onClick = { onAction(HomeUiAction.OnSpotlightFavouriteClick) },
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                AnimatedContent(
                                                    state.spotLight.isInFavourite,
                                                    label = "is in favorite animation"
                                                ) { state ->
                                                    when (state) {
                                                        true -> Icon(
                                                            imageVector = FavoriteFillIcon,
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .fillMaxSize(),
                                                            tint = MaterialTheme.colorScheme.error
                                                        )

                                                        false -> Icon(
                                                            imageVector = FavoriteEmptyIcon,
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .fillMaxSize(),
                                                            tint = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            item(
                                span = {
                                    GridItemSpan(3)
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .height(cardHeight)
                                        .fillMaxWidth()
                                        .padding(
                                            top = 95.dp,
                                            start = MaterialTheme.dimens.medium1,
                                            end = MaterialTheme.dimens.medium1
                                        )
                                ) {
                                    ExpandedFilterChip(state, onAction)

                                    ExpandedHeading(R.string.popular)

                                    BoxWithConstraints(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        val width = maxWidth / 3 - MaterialTheme.dimens.small2

                                        SpotlightSideCard(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(width),
                                            state = state,
                                            onAction = onAction
                                        )
                                    }
                                }
                            }

                            item {
                                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
                            }

                            expandedPopular(
                                list = state.popularList.drop(3),
                                onAction = onAction
                            )

                            item(
                                span = {
                                    GridItemSpan(maxLineSpan)
                                }
                            ) {
                                ExpandedHeading(R.string.top_rated)
                            }

                            expandedTopRated(state, onAction)

                            item(
                                span = {
                                    GridItemSpan(maxLineSpan)
                                }
                            ) {
                                Spacer(Modifier.height(MaterialTheme.dimens.large1))
                            }

                            item(
                                span = {
                                    GridItemSpan(maxLineSpan)
                                }
                            ) {
                                ExpandedHeading(R.string.explore)
                            }

                            expandedMore(more, onAction)
                        }

                        ExpandedTopBar(state, onAction, scroll)
                    }
                }

                false -> HomeLoadingScreen(paddingValues, cardHeight)
            }
        }
    }
}


@Preview(
    widthDp = 900,
    heightDp = 480
)
@Preview(
    widthDp = 900,
    heightDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_YES
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
            HomeExpandedSmallScreen(
                state = HomeUiState(
                    spotLight = UiPrevItem(
                        id = 1,
                        title = "Title",
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