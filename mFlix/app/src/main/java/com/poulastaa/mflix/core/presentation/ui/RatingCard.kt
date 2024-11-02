package com.poulastaa.mflix.core.presentation.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import java.util.Locale


@Composable
fun RatingCard(
    modifier: Modifier,
    rawRating: Float,
    fontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
) {
    val rating by animateFloatAsState(
        targetValue = rawRating,
        animationSpec = tween(durationMillis = 1_000),
        label = ""
    )
    val primary = MaterialTheme.colorScheme.primary
    val background = MaterialTheme.colorScheme.inversePrimary

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            drawArc(
                color = background,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = primary,
                startAngle = 270f,
                sweepAngle = (rating / 10) * 360f,
                useCenter = false,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round
                )
            )
        }

        Text(
            text = String.format(
                Locale.getDefault(),
                format = "%.2f",
                rating
            ),
            fontWeight = FontWeight.Black,
            color = primary,
            fontSize = fontSize
        )
    }
}