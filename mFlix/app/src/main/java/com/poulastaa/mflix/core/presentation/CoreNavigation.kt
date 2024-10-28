package com.poulastaa.mflix.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.BottomBarScreen
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.core.presentation.designsystem.theme.EmptyUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.HomeEmptyIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.HomeFilledIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBottomBar
import com.poulastaa.mflix.home.presentation.HomeRootScreen
import com.poulastaa.mflix.home.presentation.HomeViewModel
import com.poulastaa.mflix.profile.presentation.ProfileRootScreen
import com.poulastaa.mflix.profile.presentation.ProfileViewmodel

@Composable
fun CoreNavigation(
    windowSizeClass: WindowSizeClass,
    viewmodel: CoreViewmodel = hiltViewModel(),
    logOut: () -> Unit,
) {
    val config = LocalConfiguration.current
    val navController = rememberNavController()

    when {
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded -> {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                SideNavigationBar(viewmodel)
                CommonContent(navController, viewmodel, windowSizeClass)
            }
        }

        else -> CommonContent(navController, viewmodel, windowSizeClass)
    }

}

@Composable
private fun CommonContent(
    navController: NavHostController,
    viewmodel: CoreViewmodel,
    windowSizeClass: WindowSizeClass,
) {
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
                val profileViewModel = hiltViewModel<ProfileViewmodel>()

                ProfileRootScreen(
                    windowSizeClass = windowSizeClass,
                    viewmodel = profileViewModel
                )
            }
        }

        AnimatedVisibility(
            viewmodel.state.isBottomBarVisible &&
                    windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded,
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

@Composable
private fun SideNavigationBar(viewmodel: CoreViewmodel) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.dimens.small3),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small1)
                    .padding(vertical = MaterialTheme.dimens.small3),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewmodel.changeScreen(BottomBarScreen.HOME)
                    }
                ) {
                    Icon(
                        imageVector = if (viewmodel.state.bottomBarScreen == BottomBarScreen.HOME) HomeFilledIcon else HomeEmptyIcon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = if (viewmodel.state.bottomBarScreen == BottomBarScreen.HOME) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                AnimatedVisibility(viewmodel.state.bottomBarScreen == BottomBarScreen.HOME) {
                    Text(
                        text = stringResource(R.string.home),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    )
                }
            }
        }

        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small1)
                    .padding(vertical = MaterialTheme.dimens.small3),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewmodel.changeScreen(BottomBarScreen.PROFILE)
                    }
                ) {
                    Icon(
                        imageVector = if (viewmodel.state.bottomBarScreen == BottomBarScreen.PROFILE) FilledUserIcon else EmptyUserIcon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = if (viewmodel.state.bottomBarScreen == BottomBarScreen.PROFILE) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                AnimatedVisibility(viewmodel.state.bottomBarScreen == BottomBarScreen.PROFILE) {
                    Text(
                        text = stringResource(R.string.profile),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }
        }
    }
}