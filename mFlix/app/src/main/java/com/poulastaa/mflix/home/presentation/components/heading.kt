package com.poulastaa.mflix.home.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens

fun LazyGridScope.heading(
    @StringRes id: Int,
) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.medium1))
    }

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium1)
        ) {
            Text(
                text = stringResource(id),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }

    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.medium1))
    }
}
