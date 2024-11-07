package com.poulastaa.mflix.search.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.search.presentation.UiSearchQueryItem
import java.util.Locale


@OptIn(ExperimentalLayoutApi::class)
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
                    .aspectRatio(1f),
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

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                    ) {
                        Text(
                            text = "Rating: ${
                                String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    item.rating
                                )
                            }",
                            modifier = Modifier.padding(MaterialTheme.dimens.small2),
                            fontWeight = FontWeight.Light
                        )
                    }

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
}
