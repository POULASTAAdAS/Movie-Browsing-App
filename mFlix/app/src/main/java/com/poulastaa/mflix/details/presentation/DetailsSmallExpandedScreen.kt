package com.poulastaa.mflix.details.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowDownIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.details.presentation.components.DetailsLoadingScreen
import com.poulastaa.mflix.details.presentation.components.ExtendedDetails
import com.poulastaa.mflix.details.presentation.components.collectionItems
import com.poulastaa.mflix.details.presentation.components.detailsLazyList
import com.poulastaa.mflix.home.presentation.components.PrevMoreItemCard

@Composable
fun DetailsSmallExpandedScreen(
    state: DetailsUiState,
    recom: LazyPagingItems<UiPrevItem>,
    onAction: (DetailsUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - 20).dp

    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.isDataLoaded, label = "details screen animation") {
            when (it) {
                true -> LazyVerticalGrid(
                    columns = GridCells.Fixed(6),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        val imageReq = ImageRequest.Builder(LocalContext.current)
                            .data(state.movie.posterPath.replace("w500", "original"))
                            .build()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cardHeight)
                        ) {
                            DetailsBackground(imageReq, navigateBack)

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(60.dp + MaterialTheme.dimens.small2)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(.4f)
                                ) {
                                    Card(
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp
                                        ),
                                        shape = MaterialTheme.shapes.extraSmall,
                                        colors = if (isSystemInDarkTheme()) CardDefaults.cardColors()
                                        else CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.background
                                        )
                                    ) {
                                        Card(
                                            modifier = Modifier.padding(MaterialTheme.dimens.small2),
                                            elevation = CardDefaults.cardElevation(
                                                defaultElevation = 10.dp
                                            ),
                                            shape = MaterialTheme.shapes.extraSmall
                                        ) {
                                            SubcomposeAsyncImage(
                                                model = imageReq,
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                    }
                                }

                                Card(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.background.copy(
                                            .4f
                                        )
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(MaterialTheme.dimens.medium1),
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        ExtendedDetails(state.movie)
                                    }
                                }
                            }
                        }
                    }

                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Spacer(Modifier.height(MaterialTheme.dimens.medium1))
                    }

                    if (state.collection.items.isNotEmpty()) collectionItems(
                        collection = state.collection,
                        onAction = onAction
                    )

                    detailsLazyList(
                        title = R.string.cast_members,
                        list = state.cast,
                        itemPadding = 6.dp,
                        onClick = {
                            onAction(DetailsUiAction.OnPersonClick(it))
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
                        itemPadding = 6.dp,
                        onClick = {
                            onAction(DetailsUiAction.OnPersonClick(it))
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


@Composable
private fun BoxScope.DetailsBackground(imageReq: ImageRequest, navigateBack: () -> Unit) {
    SubcomposeAsyncImage(
        model = imageReq,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp)
            .align(Alignment.Center),
        contentScale = ContentScale.Crop
    )

    Row(
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = MaterialTheme.dimens.medium1)
    ) {
        AppBackButton(
            icon = ArrowDownIcon,
            onClick = navigateBack
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        Color.Transparent
                    )
                )
            )
    )

    Spacer(
        modifier = Modifier.Companion
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(120.dp)
            .offset(y = 60.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        if (isSystemInDarkTheme())
                            MaterialTheme.colorScheme.background
                        else MaterialTheme.colorScheme.background,
                        Color.Transparent
                    )
                )
            )
    )

    Spacer(
        modifier = Modifier.Companion
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background,
                    )
                )
            )
    )
}