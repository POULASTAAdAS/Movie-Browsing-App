package com.poulastaa.mflix.person.repsentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun PersonRootScreen(
    viewModel: PersonViewModel,
    windowSizeClass: WindowSizeClass,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            PersonCompactScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        mediumContent = {
            PersonCompactScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        expandedContent = {

        }
    )
}