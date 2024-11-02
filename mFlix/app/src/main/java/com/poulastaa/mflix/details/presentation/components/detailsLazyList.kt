package com.poulastaa.mflix.details.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.DoubleArrowIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.details.presentation.UiPerson

fun LazyGridScope.detailsLazyList(
    @StringRes title: Int,
    list: List<UiPerson>,
    itemPadding: Dp,
    onClick: (id: Long) -> Unit,
    onViewDetailsClick: () -> Unit,
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium1)
                .padding(bottom = MaterialTheme.dimens.medium1)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onViewDetailsClick
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(title),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = onViewDetailsClick
            ) {
                Icon(
                    imageVector = DoubleArrowIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
        ) {
            items(list) { person ->
                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(180.dp),
                    shape = MaterialTheme.shapes.extraSmall,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    onClick = {
                        onClick(person.id)
                    }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(itemPadding),
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
}