package com.poulastaa.mflix.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowDownIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.home.presentation.components.ItemList
import com.poulastaa.mflix.home.presentation.components.PrevMoreItemCard

@Composable
fun DetailsSmallScreen(
    state: DetailsUiState,
    recom: LazyPagingItems<UiPrevItem>,
    onAction: (DetailsUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - config.screenHeightDp / 3.1).dp

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.movie.posterPath)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
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
                        modifier = Modifier
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
                        modifier = Modifier
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
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.dimens.medium1)
                ) {
                    Text(
                        text = state.movie.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                    Text(
                        text = state.movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
            }

            if (state.collection.items.isNotEmpty()) item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text(
                    text = "From: ${state.collection.name}",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.medium1),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (state.collection.items.isNotEmpty()) item {
                Spacer(Modifier.height(MaterialTheme.dimens.small3))
            }

            if (state.collection.items.isNotEmpty()) item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                ItemList(state.collection.items.map { it.toUiPrevItem() }) { id, type ->

                }
            }

            if (state.collection.items.isNotEmpty()) item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Spacer(Modifier.height(MaterialTheme.dimens.large1))
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.dimens.medium1)
                        .padding(bottom = MaterialTheme.dimens.medium1)
                ) {
                    Text(
                        text = stringResource(R.string.cast_members),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )

                    Spacer(Modifier.weight(1f))

                    // todo add icon button for details
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
                ) {
                    items(state.cast) { person ->
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .height(180.dp),
                            shape = MaterialTheme.shapes.extraSmall,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(MaterialTheme.dimens.small2),
                                shape = MaterialTheme.shapes.extraSmall,
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    SubcomposeAsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(person.profilePath)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    Text(
                                        text = person.name,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .fillMaxWidth()
                                            .background(
                                                MaterialTheme.colorScheme.background.copy(.4f)
                                            ),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Spacer(Modifier.height(MaterialTheme.dimens.large1))
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.dimens.medium1)
                        .padding(bottom = MaterialTheme.dimens.medium1)
                ) {
                    Text(
                        text = stringResource(R.string.crew_members),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )

                    Spacer(Modifier.weight(1f))

                    // todo add icon button for details
                }

                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
                ) {
                    items(state.crew) { person ->
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .height(180.dp),
                            shape = MaterialTheme.shapes.extraSmall,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(MaterialTheme.dimens.small2),
                                shape = MaterialTheme.shapes.extraSmall,
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    SubcomposeAsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(person.profilePath)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    Text(
                                        text = person.name,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .fillMaxWidth()
                                            .background(
                                                MaterialTheme.colorScheme.background.copy(.4f)
                                            ),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Spacer(Modifier.height(MaterialTheme.dimens.large1))
            }

            if (recom.itemCount > 0) item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text(
                    text = "You may also like",
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
    }
}

//@PreviewLightDark
//@Composable
//private fun Preview() {
//    PrevThem {
//        Surface {
//            DetailsSmallScreen(
//                state = DetailsUiState(
//                    movie = UiMovieDetails(
//                        id = 912649,
//                        title = "VenomThe Last Dance",
//                        overview = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
//                        budget = 120000000,
//                        voteAverage = 6.6,
//                        releaseDate = "2024-10-22",
//                        runtime = -1,
//                        status = "Released",
//                        homepage = "https://venom.movie",
//                        productionCompany = listOf(
//                            UiMovieProductionCompany(
//                                id = 5,
//                                name = "Columbia Pictures",
//                            ),
//                            UiMovieProductionCompany(
//                                id = 84041,
//                                name = "Pascal Pictures",
//                            ),
//                            UiMovieProductionCompany(
//                                id = 53462,
//                                name = "Matt Tolmach Productions",
//                            )
//                        )
//                    )
//                ),
//                onAction = {}
//            ) {}
//        }
//    }
//}