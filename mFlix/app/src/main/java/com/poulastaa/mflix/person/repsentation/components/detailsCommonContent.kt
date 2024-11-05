package com.poulastaa.mflix.person.repsentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.PrevItemCard
import com.poulastaa.mflix.core.presentation.ui.animateText
import com.poulastaa.mflix.person.repsentation.PersonUiAction
import com.poulastaa.mflix.person.repsentation.PersonUiState

fun LazyGridScope.detailsCommonContent(
    state: PersonUiState,
    config: Configuration,
    onAction: (PersonUiAction) -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 35.dp)
                .offset(y = (-34).dp),
            shape = RoundedCornerShape(
                topStart = 34.dp,
                topEnd = 34.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            if (config.screenWidthDp < 980) Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.medium1)
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "${animateText(animateText(state.person.name))} (${animateText(state.person.role)})",
                    fontSize = if (config.screenWidthDp > 980) MaterialTheme.typography.headlineMedium.fontSize
                    else MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )

                DetailsItemFlowRow(state.person)
            }
        }
    }

    if (state.knownFor.isNotEmpty()) item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium1)
        ) {
            Text(
                text = stringResource(R.string.popular_collection),
                fontWeight = FontWeight.Bold,
                fontSize = if (config.screenWidthDp > 980) MaterialTheme.typography.headlineSmall.fontSize
                else MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.primary.copy(.7f),
                textDecoration = TextDecoration.Underline
            )
        }
    }

    items(state.knownFor) { item ->
        Box(
            modifier = Modifier
                .aspectRatio(if (config.screenWidthDp > 980) 1f / 1.3f else 1f / 1.5f)
                .padding(MaterialTheme.dimens.small2)
        ) {
            PrevItemCard(
                item = item,
                modifier = Modifier
            ) {
                onAction(PersonUiAction.OnItemClick(item.id, item.type))
            }
        }
    }
}