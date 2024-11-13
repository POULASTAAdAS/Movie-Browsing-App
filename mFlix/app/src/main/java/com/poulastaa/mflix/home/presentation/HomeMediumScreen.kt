package com.poulastaa.mflix.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteEmptyIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteFillIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.LargeSearchIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.RatingCard
import com.poulastaa.mflix.home.presentation.components.HomeLoadingScreen
import com.poulastaa.mflix.home.presentation.components.homeCommonContent
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMediumScreen(
    state: HomeUiState,
    more: LazyPagingItems<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - (config.screenHeightDp / 6.3)).dp

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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

                    homeCommonContent(state, onAction, more)
                }

                false -> HomeLoadingScreen(paddingValues, cardHeight)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun LazyGridScope.spotlightMediumCard(
    cardHeight: Dp,
    userName: String,
    spotlight: UiPrevItem,
    onAction: (HomeUiAction) -> Unit,
    scroll: TopAppBarScrollBehavior,
) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onAction(HomeUiAction.OnItemClick(spotlight.id, spotlight.type))
                        }
                    ),
                contentScale = ContentScale.FillBounds,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(spotlight.imageUrl)
                    .build(),
                contentDescription = null,
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = MovieIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = MaterialTheme.dimens.medium1)
                        )
                    }
                },
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )

            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = MaterialTheme.dimens.medium1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stringResource(R.string.app_name)} ",
                            fontStyle = FontStyle.Italic,
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(Modifier.width(MaterialTheme.dimens.small3))

                        Text(
                            text = "Hello $userName",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                onAction(HomeUiAction.OnSearchClick)
                            }
                        ) {
                            Icon(
                                imageVector = LargeSearchIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                scrollBehavior = scroll,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background.copy(.6f),
                                Color.Transparent,
                            )
                        )
                    )
                    .padding(vertical = MaterialTheme.dimens.medium2)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.25f)
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
                        text = spotlight.title,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(.9f)
                    )

                    RatingCard(
                        rawRating = spotlight.rating.toFloat(),
                        modifier = Modifier.size(60.dp),
                    )
                }

                Spacer(Modifier.height(MaterialTheme.dimens.small1))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = spotlight.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier.fillMaxWidth(.8f)
                    )

                    Spacer(Modifier.weight(1f))

                    IconButton(
                        onClick = { onAction(HomeUiAction.OnSpotlightFavouriteClick) },
                        modifier = Modifier.size(50.dp)
                    ) {
                        AnimatedContent(
                            spotlight.isInFavourite,
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