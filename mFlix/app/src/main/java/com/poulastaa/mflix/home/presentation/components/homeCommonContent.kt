package com.poulastaa.mflix.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.HomeUiAction
import com.poulastaa.mflix.home.presentation.HomeUiState


fun LazyGridScope.homeCommonContent(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    more: LazyPagingItems<UiPrevItem>
) {
    heading(R.string.popular)

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        ItemList(state.popularList) { id, type ->
            onAction(
                HomeUiAction.OnItemClick(
                    id = id,
                    type = type
                )
            )
        }
    }

    heading(R.string.top_rated)

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        ItemList(state.topRated) { id, type ->
            onAction(
                HomeUiAction.OnItemClick(
                    id = id,
                    type = type
                )
            )
        }
    }

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.large1))
    }

    heading(R.string.explore)

    items(more.itemCount) { index ->
        more[index]?.let { item ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f / 1.3f)
                    .padding(MaterialTheme.dimens.small2)
            ) {
                PrevMoreItemCard(
                    modifier = Modifier,
                    item = item,
                    onClick = {
                        onAction(HomeUiAction.OnItemClick(item.id, item.type))
                    }
                )
            }
        }
    }
}