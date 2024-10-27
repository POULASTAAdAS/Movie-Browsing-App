package com.poulastaa.mflix.home.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteEmptyIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FavoriteFillIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens

@Composable
fun SpotlightSmallCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    rating: Double,
    isInFavourite: Boolean,
    coverImage: String,
    onFavouriteClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp,
                pressedElevation = 0.dp
            ),
            onClick = onCardClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    model = ImageRequest.Builder(context)
                        .data(coverImage)
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

                Spacer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        )
                        .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                )

                Spacer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(.45f)
                        .blur(10.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(.5f),
                                    Color.White.copy(.5f),
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.35f)
                        .align(Alignment.BottomCenter)
                        .padding(MaterialTheme.dimens.small3),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text( // todo change to icons
                            text = "Rating: $rating",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                        ) {
                            Text(
                                text = title,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Black
                            )

                            Spacer(Modifier.height(MaterialTheme.dimens.small1))

                            Text(
                                text = description,
                                fontWeight = FontWeight.Medium,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                color = Color.Black
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = onFavouriteClick,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                AnimatedContent(isInFavourite, label = "is in favorite animation") {
                                    when (it) {
                                        true -> Icon(
                                            imageVector = FavoriteFillIcon,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .aspectRatio(1f)
                                                .padding(MaterialTheme.dimens.small3),
                                            tint = MaterialTheme.colorScheme.error
                                        )

                                        false -> Icon(
                                            imageVector = FavoriteEmptyIcon,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .aspectRatio(1f)
                                                .padding(MaterialTheme.dimens.small3),
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
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimens.medium1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                SpotlightSmallCard(
                    title = "That Cool Movie",
                    description = "This is a cool movie about a boy and a girl who are in love",
                    rating = 4.5,
                    isInFavourite = true,
                    coverImage = "",
                    onFavouriteClick = { },
                    onCardClick = { }
                )
            }
        }
    }
}