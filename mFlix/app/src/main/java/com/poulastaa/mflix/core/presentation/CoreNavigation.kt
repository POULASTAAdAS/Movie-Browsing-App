package com.poulastaa.mflix.core.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBottomBar
import com.poulastaa.mflix.home.presentation.HomeRootScreen
import com.poulastaa.mflix.home.presentation.HomeViewModel


@Composable
fun CoreNavigation(
    windowSizeClass: WindowSizeClass,
    viewmodel: CoreViewmodel = hiltViewModel(),
    logOut: () -> Unit,
) {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = viewmodel.state.screen
        ) {
            composable<AppScreen.Home> {
                val homeViewModel = hiltViewModel<HomeViewModel>()

                HomeRootScreen(
                    windowSizeClass = windowSizeClass,
                    viewModel = homeViewModel
                )
            }

            composable<AppScreen.Profile> {

            }
        }

        AnimatedVisibility(
            viewmodel.state.isBottomBarVisible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(MaterialTheme.dimens.medium3),
            enter = fadeIn(animationSpec = tween(400)) +
                    slideInVertically(animationSpec = tween(400)),
            exit = fadeOut(animationSpec = tween(400)) +
                    slideOutVertically(animationSpec = tween(400))
        ) {
            AppBottomBar(
                screen = viewmodel.state.bottomBarScreen,
                modifier = Modifier
                    .fillMaxWidth(
                        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium) .65f else 1f
                    )
                    .padding(horizontal = MaterialTheme.dimens.medium1),
                onClick = viewmodel::changeScreen
            )
        }
    }
}