package com.poulastaa.mflix.profile.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun ProfileRootScreen(
    windowSizeClass: WindowSizeClass,
    viewmodel: ProfileViewmodel,
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            ProfileSmallScreen(
                state = state,
                onAction = viewmodel::onAction
            )
        },
        mediumContent = {
            ProfileSmallScreen( // todo fix height and width
                state = state,
                onAction = viewmodel::onAction
            )
        },
        expandedContent = {

        }
    )
}