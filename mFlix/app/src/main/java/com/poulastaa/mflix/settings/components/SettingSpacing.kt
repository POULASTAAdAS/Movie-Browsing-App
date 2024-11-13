package com.poulastaa.mflix.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SettingsFrontSpace() {
    Spacer(
        Modifier
            .clip(CircleShape)
            .height(34.dp)
            .width(3.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}