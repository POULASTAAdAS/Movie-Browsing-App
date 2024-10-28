package com.poulastaa.mflix.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.poulastaa.mflix.core.presentation.designsystem.theme.SearchIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.PrevItemCard
import com.poulastaa.mflix.home.presentation.components.HomeLoadingScreen
import com.poulastaa.mflix.home.presentation.components.PrevMoreItemCard
import kotlinx.coroutines.flow.flowOf
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeExpandedScreen(
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
                cardHeight = (it.size.height / 2.4).roundToInt()
            },
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
                                        .height(cardHeight.dp)
                                ) {
                                    SubcomposeAsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(MaterialTheme.shapes.small),
                                        contentScale = ContentScale.Crop,
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(
                                                state.spotLight.imageUrl.replace(
                                                    oldValue = "https://image.tmdb.org/t/p/w500",
                                                    newValue = "https://image.tmdb.org/t/p/original"
                                                )
                                            )
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


                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(.22f)
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
                                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
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
                                        .height(cardHeight.dp)
                                        .fillMaxWidth()
                                        .padding(
                                            top = 80.dp,
                                            start = MaterialTheme.dimens.medium1,
                                            end = MaterialTheme.dimens.medium1
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
                                    ) {
                                        FilterChip(
                                            selected = state.filterType == UiHomeFilterType.ALL,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnFilterTypeChange(
                                                        UiHomeFilterType.ALL
                                                    )
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(R.string.all),
                                                    fontWeight = if (state.filterType == UiHomeFilterType.ALL) FontWeight.SemiBold
                                                    else FontWeight.Normal,
                                                    letterSpacing = 1.sp
                                                )
                                            },
                                            border = if (state.filterType == UiHomeFilterType.ALL) BorderStroke(
                                                width = 2.dp,
                                                color = MaterialTheme.colorScheme.primary
                                            ) else FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = false
                                            )
                                        )

                                        FilterChip(
                                            selected = state.filterType == UiHomeFilterType.MOVIE,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnFilterTypeChange(
                                                        UiHomeFilterType.MOVIE
                                                    )
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(R.string.movie),
                                                    fontWeight = if (state.filterType == UiHomeFilterType.MOVIE) FontWeight.SemiBold
                                                    else FontWeight.Normal,
                                                    letterSpacing = 1.sp
                                                )
                                            },
                                            border = if (state.filterType == UiHomeFilterType.MOVIE) BorderStroke(
                                                width = 2.dp,
                                                color = MaterialTheme.colorScheme.primary
                                            ) else FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = false
                                            )
                                        )

                                        FilterChip(
                                            selected = state.filterType == UiHomeFilterType.TV,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnFilterTypeChange(
                                                        UiHomeFilterType.TV
                                                    )
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(R.string.tv_shows),
                                                    fontWeight = if (state.filterType == UiHomeFilterType.TV) FontWeight.SemiBold
                                                    else FontWeight.Normal,
                                                    letterSpacing = 1.sp
                                                )
                                            },
                                            border = if (state.filterType == UiHomeFilterType.TV) BorderStroke(
                                                width = 2.dp,
                                                color = MaterialTheme.colorScheme.primary
                                            ) else FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = false
                                            )
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = MaterialTheme.dimens.medium1)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.popular),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = MaterialTheme.typography.headlineMedium.fontSize
                                        )
                                    }

                                    BoxWithConstraints(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        val height = maxHeight / 2 - MaterialTheme.dimens.small2
                                        val width = maxWidth / 3 - MaterialTheme.dimens.small2

                                        FlowRow(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.spacedBy(
                                                MaterialTheme.dimens.small2
                                            ),
                                            verticalArrangement = Arrangement.spacedBy(
                                                MaterialTheme.dimens.small2
                                            )
                                        ) {
                                            state.popularList.forEach { item ->
                                                PrevItemCard(
                                                    modifier = Modifier
                                                        .width(width)
                                                        .height(height)
                                                        .padding(MaterialTheme.dimens.small1),
                                                    item = item,
                                                    onClick = {
                                                        onAction(
                                                            HomeUiAction.OnItemClick(
                                                                item.id,
                                                                item.type
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            item {
                                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
                            }

                            item(
                                span = {
                                    GridItemSpan(maxLineSpan)
                                }
                            ) {
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
                                    contentPadding = PaddingValues(end = MaterialTheme.dimens.medium1)
                                ) {
                                    items(state.popularList.drop(6)) { item ->
                                        PrevItemCard(
                                            modifier = Modifier
                                                .width(150.dp)
                                                .padding(MaterialTheme.dimens.small2),
                                            item = item,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnItemClick(
                                                        item.id,
                                                        item.type
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                            item(
                                span = {
                                    GridItemSpan(maxLineSpan)
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = MaterialTheme.dimens.medium1)
                                ) {
                                    Text(
                                        text = stringResource(R.string.top_rated),
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
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
                                    contentPadding = PaddingValues(end = MaterialTheme.dimens.medium1)
                                ) {
                                    items(state.topRated.drop(6)) { item ->
                                        PrevItemCard(
                                            modifier = Modifier
                                                .width(150.dp)
                                                .padding(MaterialTheme.dimens.small2),
                                            item = item,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnItemClick(
                                                        item.id,
                                                        item.type
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }

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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = MaterialTheme.dimens.medium1)
                                ) {
                                    Text(
                                        text = stringResource(R.string.explore),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                                    )
                                }
                            }

                            items(more.itemCount) { index ->
                                more[index]?.let { item ->
                                    Box(
                                        modifier = Modifier
                                            .aspectRatio(1f / 1.3f)
                                            .padding(MaterialTheme.dimens.small2)
                                    ) {
                                        PrevMoreItemCard(
                                            modifier = Modifier,
                                            item = item,
                                            onClick = {
                                                onAction(
                                                    HomeUiAction.OnItemClick(
                                                        item.id,
                                                        item.type
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }

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
                                        text = "Hello ${state.user.name}",
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
                                            imageVector = SearchIcon,
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
                    }
                }

                false -> HomeLoadingScreen(paddingValues, cardHeight)
            }
        }
    }
}

@Preview(
    widthDp = 1280,
    heightDp = 650
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
            HomeExpandedScreen(
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