package com.poulastaa.mflix.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens


@Composable
fun ItemList(
    list: List<UiPrevItem>,
    onClick: (id: Long, type: UiPrevItemType) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1)
    ) {
        items(list) { item ->
            PrevItemCard(
                modifier = Modifier
                    .width(150.dp)
                    .padding(MaterialTheme.dimens.small2),
                item = item,
                onClick = {
                    onClick(item.id, item.type)
                }
            )
        }
    }
}