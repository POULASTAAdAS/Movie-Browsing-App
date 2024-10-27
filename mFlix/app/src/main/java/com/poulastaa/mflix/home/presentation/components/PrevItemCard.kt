package com.poulastaa.mflix.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens

@Composable
fun PrevItemCard(
    modifier: Modifier = Modifier,
    item: UiPrevItem,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.extraSmall,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        )
    ) {
        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraSmall,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp,
                pressedElevation = 0.dp
            ),
            onClick = onClick
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
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
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        PrevItemCard(
            item = UiPrevItem(),
            modifier = Modifier
                .width(130.dp)
                .height(180.dp),
            onClick = {

            }
        )
    }
}