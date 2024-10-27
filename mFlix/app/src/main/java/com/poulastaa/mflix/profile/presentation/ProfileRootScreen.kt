package com.poulastaa.mflix.profile.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun ProfileRootScreen(
    windowSizeClass: WindowSizeClass,
    viewmodel: ProfileViewmodel,
) {
    val context = LocalContext.current

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {

        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}