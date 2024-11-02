package com.poulastaa.mflix.details.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.details.presentation.components.DetailsLoadingScreen
import com.poulastaa.mflix.details.presentation.components.collectionItems
import com.poulastaa.mflix.details.presentation.components.detailsItemDetails
import com.poulastaa.mflix.details.presentation.components.detailsLazyList
import com.poulastaa.mflix.details.presentation.components.detailsSpotlight
import com.poulastaa.mflix.home.presentation.components.PrevMoreItemCard
import kotlinx.coroutines.flow.flowOf

@Composable
fun DetailsSmallScreen(
    state: DetailsUiState,
    recom: LazyPagingItems<UiPrevItem>,
    onAction: (DetailsUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - config.screenHeightDp / 3.1).dp

    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.isDataLoaded, label = "details screen animation") {
            when (it) {
                true -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    detailsSpotlight(
                        cardHeight = cardHeight,
                        image = state.movie.posterPath,
                        navigateBack = navigateBack
                    )

                    detailsItemDetails(state.movie)

                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.medium3))
                    }

                    if (state.collection.items.isNotEmpty()) collectionItems(
                        collection = state.collection,
                        onAction = onAction
                    )

                    detailsLazyList(
                        title = R.string.cast_members,
                        list = state.cast,
                        itemPadding = 4.dp,
                        onClick = { id ->
                            onAction(DetailsUiAction.OnPersonClick(id))
                        },
                        onViewDetailsClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onAction(DetailsUiAction.OnCastMemberDetailsClick)
                        }
                    )
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.large2))
                    }

                    detailsLazyList(
                        title = R.string.crew_members,
                        list = state.crew,
                        itemPadding = 4.dp,
                        onClick = { id ->
                            onAction(DetailsUiAction.OnPersonClick(id))
                        },
                        onViewDetailsClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onAction(DetailsUiAction.OnCrewMemberDetailsClick)
                        }
                    )

                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.large1))
                    }

                    if (recom.itemCount > 0) item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Text(
                            text = stringResource(R.string.you_may_also_like),
                            fontWeight = FontWeight.Black,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(start = MaterialTheme.dimens.medium1)
                                .padding(bottom = MaterialTheme.dimens.small2)
                        )
                    }

                    items(recom.itemCount) { index ->
                        recom[index]?.let { item ->
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f / 1.3f)
                                    .padding(MaterialTheme.dimens.small2)
                            ) {
                                PrevMoreItemCard(item = item) {
                                    onAction(DetailsUiAction.OnItemClick(item.id, item.type))
                                }
                            }
                        }
                    }

                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Spacer(
                            Modifier
                                .navigationBarsPadding()
                                .height(40.dp)
                        )
                    }
                }

                false -> DetailsLoadingScreen(paddingValues, cardHeight)
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
            DetailsSmallScreen(
                DetailsUiState(

                ),
                dummyMoreItems.collectAsLazyPagingItems(),
                {},
                {}
            )
        }
    }
}