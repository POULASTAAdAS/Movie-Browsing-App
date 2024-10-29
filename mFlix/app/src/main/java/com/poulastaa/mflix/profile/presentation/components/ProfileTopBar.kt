package com.poulastaa.mflix.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileTopBar(scroll: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.medium1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "m ",
                    fontStyle = FontStyle.Italic,
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.small3,
                    ),
                )

                Spacer(Modifier.width(MaterialTheme.dimens.small3))

                Text(
                    text = stringResource(R.string.profile),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
        scrollBehavior = scroll,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(.6f),
                        Color.Transparent,
                    )
                )
            )
            .padding(vertical = MaterialTheme.dimens.medium2)
    )
}