package com.poulastaa.mflix.core.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem

@Composable
fun AppBackButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            AppBackButton(icon = Icons.AutoMirrored.Filled.ArrowBack) {

            }
        }
    }
}