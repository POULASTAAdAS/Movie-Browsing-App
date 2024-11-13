package com.poulastaa.mflix.profile.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun ProfileRootScreen(
    windowSizeClass: WindowSizeClass,
    viewmodel: ProfileViewmodel,
    onNavigateToDetails: (Long, PrevItemType) -> Unit,
    onNavigateToSetting: () -> Unit
) {
    val config = LocalConfiguration.current
    val state by viewmodel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewmodel.uiEvent) { event ->
        when (event) {
            is ProfileUiEvent.OnItemClick -> onNavigateToDetails(event.id,event.type)
            ProfileUiEvent.NavigateToSetting -> onNavigateToSetting()
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            ProfileSmallScreen(
                state = state,
                onAction = viewmodel::onAction
            )
        },
        mediumContent = {
            ProfileSmallScreen(
                state = state,
                onAction = viewmodel::onAction
            )
        },
        expandedContent = {
            if (config.screenWidthDp > 980) ProfileExtendedScreen(
                state = state,
                onAction = viewmodel::onAction
            ) else ProfileSmallExtendedScreen(
                state = state,
                onAction = viewmodel::onAction
            )
        }
    )
}