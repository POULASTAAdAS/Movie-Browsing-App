package com.poulastaa.mflix.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.home.presentation.UiHomeFilterType

@Composable
fun HomeFilterChip(
    modifier: Modifier = Modifier,
    filterType: UiHomeFilterType,
    onClick: (UiHomeFilterType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            selected = filterType == UiHomeFilterType.ALL,
            onClick = {
                onClick(UiHomeFilterType.ALL)
            },
            label = {
                Text(
                    text = stringResource(R.string.all),
                    fontWeight = if (filterType == UiHomeFilterType.ALL) FontWeight.SemiBold
                    else FontWeight.Normal,
                    letterSpacing = 1.sp
                )
            },
            border = if (filterType == UiHomeFilterType.ALL) BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ) else FilterChipDefaults.filterChipBorder(enabled = true, selected = false)
        )

        FilterChip(
            selected = filterType == UiHomeFilterType.MOVIE,
            onClick = {
                onClick(UiHomeFilterType.MOVIE)
            },
            label = {
                Text(
                    text = stringResource(R.string.movie),
                    fontWeight = if (filterType == UiHomeFilterType.MOVIE) FontWeight.SemiBold
                    else FontWeight.Normal,
                    letterSpacing = 1.sp
                )
            },
            border = if (filterType == UiHomeFilterType.MOVIE) BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ) else FilterChipDefaults.filterChipBorder(enabled = true, selected = false)
        )

        FilterChip(
            selected = filterType == UiHomeFilterType.TV,
            onClick = {
                onClick(UiHomeFilterType.TV)
            },
            label = {
                Text(
                    text = stringResource(R.string.tv_shows),
                    fontWeight = if (filterType == UiHomeFilterType.TV) FontWeight.SemiBold
                    else FontWeight.Normal,
                    letterSpacing = 1.sp
                )
            },
            border = if (filterType == UiHomeFilterType.TV) BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ) else FilterChipDefaults.filterChipBorder(enabled = true, selected = false)
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        HomeFilterChip(filterType = UiHomeFilterType.ALL) { }
    }
}