package com.poulastaa.mflix.search.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.MovieIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.search.presentation.UiSearchQueryItem

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