package com.poulastaa.mflix.core.presentation

import com.poulastaa.mflix.core.domain.model.BottomBarScreen
import com.poulastaa.mflix.core.navigation.AppScreen

data class CoreUiState(
    val screen: AppScreen = AppScreen.Home,
    val isVisible: Boolean = true,
) {
    val bottomBarScreen = if (screen == AppScreen.Home) BottomBarScreen.HOME
    else BottomBarScreen.PROFILE

    val isBottomBarVisible = (screen == AppScreen.Home || screen == AppScreen.Profile) && isVisible
}
