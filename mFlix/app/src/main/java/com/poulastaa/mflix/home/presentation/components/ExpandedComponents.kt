package com.poulastaa.mflix.home.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.SearchIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.PrevItemCard
import com.poulastaa.mflix.home.presentation.HomeUiAction
import com.poulastaa.mflix.home.presentation.HomeUiState
import com.poulastaa.mflix.home.presentation.UiHomeFilterType

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExpandedTopBar(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    scroll: TopAppBarScrollBehavior,
) {
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

fun LazyGridScope.expandedMore(
    more: LazyPagingItems<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
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

fun LazyGridScope.expandedTopRated(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
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
            items(state.topRated) { item ->
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
}

fun LazyGridScope.expandedPopular(
    list: List<UiPrevItem>,
    onAction: (HomeUiAction) -> Unit,
) {
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
            items(list) { item ->
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
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun SpotlightSideCard(
    modifier: Modifier,
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
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
                modifier = modifier
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

@Composable
fun ExpandedHeading(
    @StringRes id: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.medium1)
    ) {
        Text(
            text = stringResource(id),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
    }
}

@Composable
fun ExpandedFilterChip(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
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
}

@Composable
fun ExpandedSpotlight(state: HomeUiState) {
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
}