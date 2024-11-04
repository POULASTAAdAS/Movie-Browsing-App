package com.poulastaa.mflix.person

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.profile.presentation.ProfileExtendedScreen
import com.poulastaa.mflix.profile.presentation.ProfileSmallExtendedScreen
import com.poulastaa.mflix.profile.presentation.ProfileSmallScreen

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

        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}