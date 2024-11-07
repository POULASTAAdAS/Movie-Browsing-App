package com.poulastaa.mflix.search.presentation

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.CloseIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.GridIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.ListIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.SmallSearchIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.components.HomeFilterChip
import com.poulastaa.mflix.search.presentation.components.SearchGridItem
import com.poulastaa.mflix.search.presentation.components.SearchListItem
import kotlinx.coroutines.flow.flowOf

@Stable
@Composable
fun getGridSize(window: WindowWidthSizeClass): Int {

    return when (window) {
        WindowWidthSizeClass.Medium -> 5
        WindowWidthSizeClass.Expanded -> 6
        else -> 3
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    data: LazyPagingItems<UiSearchQueryItem>,
    onAction: (SearchUiAction) -> Unit,
) {
    val config = LocalConfiguration.current
    val window = calculateWindowSizeClass(LocalContext.current as Activity).widthSizeClass
    val gridSize = getGridSize(window)

    val haptic = LocalHapticFeedback.current
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize),
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
            Spacer(Modifier.height(MaterialTheme.dimens.medium3))
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
            span = { GridItemSpan(if (state.viewType == UiSearchItemViewType.LIST) gridSize else 1) }
        ) { index ->
            data[index]?.let { item ->
                when (state.viewType) {
                    UiSearchItemViewType.GRID -> SearchGridItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                if (window == WindowWidthSizeClass.Expanded && config.screenWidthDp > 980) 280.dp
                                else 180.dp
                            )
                            .padding(MaterialTheme.dimens.small1),
                        item = item
                    ) {
                        onAction(SearchUiAction.OnItemClick(item.id, item.type))
                    }

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
                        ) {
                            onAction(SearchUiAction.OnItemClick(item.id, item.type))
                        }
                    }
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
            SearchScreen(
                state = SearchUiState(),
                dummyMoreItems.collectAsLazyPagingItems(),
                onAction = {}
            )
        }
    }
}