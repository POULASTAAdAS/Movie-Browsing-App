package com.poulastaa.mflix.details.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowDownIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton

@Composable
fun DetailsSmallScreen(
    state: DetailsUiState,
    onAction: (DetailsUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - config.screenHeightDp / 3.5).dp
    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
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
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.dimens.medium1)
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = state.movie.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(MaterialTheme.dimens.small1))

                    Text(
                        text = state.movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            item {
                Spacer(Modifier.height(MaterialTheme.dimens.large1))

                Text(
                    text = stringResource(R.string.cast_members),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.medium1)
                )

                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
            }

            item {
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
                                }
                            }
                        }
                    }
                }
            }

            item {
                var areCrewMemVisible by rememberSaveable { mutableStateOf(false) }

                Spacer(Modifier.height(MaterialTheme.dimens.large1))

                Column(
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.medium1)
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                areCrewMemVisible = !areCrewMemVisible
                            }
                    ) {
                        Text(
                            text = stringResource(R.string.crew_members),
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        )

                        Spacer(Modifier.weight(1f))

                        Icon(
                            imageVector = ArrowDownIcon,
                            contentDescription = null,
                        )
                    }

                    AnimatedVisibility(
                        visible = areCrewMemVisible,
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimens.medium1),
                        enter = fadeIn(animationSpec = tween(200)) +
                                slideInVertically(animationSpec = tween(400)),
                        exit = fadeOut(animationSpec = tween(200)) +
                                slideOutVertically(animationSpec = tween(400))
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
                        ) {
                            items(state.cast) { person -> // todo change to crew
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
                                        }
                                    }
                                }
                            }
                        }
                    }
                }



                Spacer(Modifier.height(MaterialTheme.dimens.medium1))
            }

            item {
                Spacer(
                    Modifier
                        .navigationBarsPadding()
                        .height(40.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            DetailsSmallScreen(
                state = DetailsUiState(
                    movie = UiMovieDetails(
                        id = 912649,
                        title = "VenomThe Last Dance",
                        overview = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
                        budget = 120000000,
                        voteAverage = 6.6,
                        releaseDate = "2024-10-22",
                        runtime = -1,
                        status = "Released",
                        homepage = "https://venom.movie",
                        productionCompany = listOf(
                            UiMovieProductionCompany(
                                id = 5,
                                name = "Columbia Pictures",
                            ),
                            UiMovieProductionCompany(
                                id = 84041,
                                name = "Pascal Pictures",
                            ),
                            UiMovieProductionCompany(
                                id = 53462,
                                name = "Matt Tolmach Productions",
                            )
                        )
                    )
                ),
                onAction = {}
            ) {}
        }
    }
}