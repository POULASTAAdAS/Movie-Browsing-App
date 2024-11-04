package com.poulastaa.mflix.details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.RatingCard
import com.poulastaa.mflix.details.presentation.UiMovieDetails

@Composable
fun ExtendedDetails(
    movie: UiMovieDetails,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.85f)
        ) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Black,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = movie.overview,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Light,
                maxLines = 5
            )
        }

        Spacer(Modifier.weight(1f))

        RatingCard(
            modifier = Modifier.size(56.dp),
            rawRating = movie.voteAverage
        )
    }

    Spacer(Modifier.height(MaterialTheme.dimens.small3))

    Text(
        text = "Release Date: ${movie.releaseDate}",
        fontWeight = FontWeight.Medium,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize
    )
}