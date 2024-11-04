package com.poulastaa.mflix.details.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens

fun LazyGridScope.detailsReleaseDate(date: String) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium1)
                .padding(top = MaterialTheme.dimens.medium1)
        ) {
            Text(
                text = "Release Date: $date",
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.medium1)
                .padding(top = MaterialTheme.dimens.medium1)
        )
    }
}
