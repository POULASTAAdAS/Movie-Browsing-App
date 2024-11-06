package com.poulastaa.mflix.search.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.CloseIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.GridIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.ListIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.SmallSearchIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.HomeFilterChip
import kotlinx.coroutines.flow.flowOf

const val MAX_LINE = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCompactScreen(
    state: SearchUiState,
    data: LazyPagingItems<UiSearchQueryItem>,
    onAction: (SearchUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val haptic = LocalHapticFeedback.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(MAX_LINE),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scroll.nestedScrollConnection),
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            TopAppBar(
                modifier = Modifier.systemBarsPadding(),
                scrollBehavior = scroll,
                windowInsets = WindowInsets(0, 0, 0, 0),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .systemBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.87f)
                                .shadow(
                                    elevation = if (isFocused) 5.dp else 0.dp,
                                    shape = CircleShape
                                ),
                            shape = CircleShape
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                interactionSource = interactionSource,
                                value = state.query,
                                onValueChange = {
                                    onAction(SearchUiAction.OnSearchQueryChange(it))
                                },
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.search),
                                        color = MaterialTheme.colorScheme.primary.copy(.8f)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = SmallSearchIcon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary.copy(.8f)
                                    )
                                },
                                singleLine = true,
                                trailingIcon = {
                                    AnimatedVisibility(
                                        visible = state.query.isNotEmpty(),
                                        enter = fadeIn(tween(400)),
                                        exit = fadeOut(tween(400))
                                    ) {
                                        Icon(
                                            imageVector = CloseIcon,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary.copy(.5f),
                                            modifier = Modifier.clickable(
                                                indication = null,
                                                interactionSource = null,
                                                onClick = {
                                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                    onAction(SearchUiAction.OnClear)
                                                }
                                            )
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                )
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        AnimatedContent(
                            state.viewType, label = "",
                            modifier = Modifier.padding(end = 18.dp)
                        ) {
                            when (it) {
                                UiSearchItemViewType.LIST -> Icon(
                                    imageVector = GridIcon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(.9f),
                                    modifier = Modifier
                                        .clickable(
                                            indication = null,
                                            interactionSource = null,
                                            onClick = {
                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                onAction(SearchUiAction.OnItemViewTypeToggle)
                                            }
                                        )
                                )

                                UiSearchItemViewType.GRID -> Icon(
                                    imageVector = ListIcon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(.9f),
                                    modifier = Modifier
                                        .clickable(
                                            indication = null,
                                            interactionSource = null,
                                            onClick = {
                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                onAction(SearchUiAction.OnItemViewTypeToggle)
                                            }
                                        )
                                )
                            }
                        }
                    }
                }
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            HomeFilterChip(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium1),
                filterType = state.searchType.toUiHomeSearchType()
            ) {
                onAction(SearchUiAction.OnSearchTypeToggle(it.toAppSearchType()))
            }
        }


        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Spacer(Modifier.height(MaterialTheme.dimens.medium3))
        }

        items(
            data.itemCount,
            span = { GridItemSpan(if (state.viewType == UiSearchItemViewType.LIST) MAX_LINE else 1) }
        ) { index ->
            data[index]?.let { item ->
                AnimatedContent(
                    state.viewType, label = "",
                ) { type ->
                    when (type) {
                        UiSearchItemViewType.GRID -> SearchGridItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .padding(MaterialTheme.dimens.small1),
                            item = item
                        ) { }

                        UiSearchItemViewType.LIST -> Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .padding(bottom = MaterialTheme.dimens.small3)
                        ) {
                            SearchListItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = MaterialTheme.dimens.small3),
                                item = item
                            ) { }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchGridItem(
    modifier: Modifier = Modifier,
    item: UiSearchQueryItem,
    onClick: () -> Unit,
) {
    Card(
        modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.small1),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.extraSmall,
            onClick = onClick
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.coverImage)
                    .build(),
                contentDescription = null,
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = MovieIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(60.dp)
                        )
                    }
                },
                error = {
                    Box(Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = MovieIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(60.dp)
                        )
                    }
                },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SearchListItem(
    modifier: Modifier = Modifier,
    item: UiSearchQueryItem,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 0.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.35f),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimens.small1),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 0.dp
                    ),
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.coverImage)
                            .build(),
                        contentDescription = null,
                        loading = {
                            Box(Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = MovieIcon,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(60.dp)
                                )
                            }
                        },
                        error = {
                            Box(Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = MovieIcon,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(60.dp)
                                )
                            }
                        },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.medium1),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(MaterialTheme.dimens.small1))

                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    ),
                ) {
                    Text(
                        text = "Rating: ${item.rating}",
                        modifier = Modifier.padding(MaterialTheme.dimens.small2),
                        fontWeight = FontWeight.Light
                    )
                }

                Spacer(Modifier.height(MaterialTheme.dimens.small3))

                if (item.releaseDate.isNotEmpty()) Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    )
                ) {
                    Text(
                        text = "Release Date: ${item.releaseDate}",
                        modifier = Modifier.padding(MaterialTheme.dimens.small2),
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    val dummyMoreItems = flowOf(
        PagingData.from(
            (1..10).map {
                UiSearchQueryItem(
                    id = it.toLong(),
                    title = "That Cool title: $it",
                )
            }
        )
    )

    PrevThem {
        Surface {
            SearchCompactScreen(
                state = SearchUiState(),
                dummyMoreItems.collectAsLazyPagingItems(),
                onAction = {}
            ) { }
        }
    }
}