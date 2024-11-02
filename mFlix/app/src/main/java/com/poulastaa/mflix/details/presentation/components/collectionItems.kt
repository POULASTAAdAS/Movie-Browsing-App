package com.poulastaa.mflix.details.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.details.presentation.DetailsUiAction
import com.poulastaa.mflix.details.presentation.UiMovieCollection
import com.poulastaa.mflix.details.presentation.toUiPrevItem
import com.poulastaa.mflix.home.presentation.components.ItemList

fun LazyGridScope.collectionItems(
    collection: UiMovieCollection,
    onAction: (DetailsUiAction) -> Unit,
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "From: ${collection.name}",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = MaterialTheme.dimens.medium1),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

    item {
        Spacer(Modifier.height(MaterialTheme.dimens.small3))
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        ItemList(collection.items.map { it.toUiPrevItem() }) { id, type ->
            onAction(DetailsUiAction.OnItemClick(id, type))
        }
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.large1))
    }
}