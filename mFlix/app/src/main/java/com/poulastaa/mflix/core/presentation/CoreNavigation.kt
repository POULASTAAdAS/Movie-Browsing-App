package com.poulastaa.mflix.core.presentation

import android.content.Context
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.BottomBarScreen
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.core.presentation.designsystem.theme.EmptyUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.HomeEmptyIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.HomeFilledIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBottomBar
import com.poulastaa.mflix.details.presentation.DetailsRootScreen
import com.poulastaa.mflix.details.presentation.DetailsViewModel
import com.poulastaa.mflix.home.presentation.HomeRootScreen
import com.poulastaa.mflix.home.presentation.HomeViewModel
import com.poulastaa.mflix.person.repsentation.PersonRootScreen
import com.poulastaa.mflix.person.repsentation.PersonViewModel
import com.poulastaa.mflix.profile.presentation.ProfileRootScreen
import com.poulastaa.mflix.profile.presentation.ProfileViewmodel
import com.poulastaa.mflix.search.presentation.SearchRootScreen
import com.poulastaa.mflix.search.presentation.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

const val DEFAULT_ANIMATION_TIME = 600

@Composable
fun CoreNavigation(
    windowSizeClass: WindowSizeClass,
    viewmodel: CoreViewmodel = hiltViewModel(),
    logOut: () -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collectLatest { backStack ->
            viewmodel.update(
                state = backStack.destination.route?.contains("Details")?.not() ?: false &&
                        backStack.destination.route?.contains("Person")?.not() ?: false &&
                        backStack.destination.route?.contains("Search")?.not() ?: false
            )
        }
    }

    when {
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded -> {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                SideNavigationBar(viewmodel)

                Spacer(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary.copy(.5f))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.background.copy(.6f),
                                    Color.Transparent,
                                )
                            )
                        )
                )

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
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = viewmodel.state.screen
        ) {
            composable<AppScreen.Home> {
                val homeViewModel = hiltViewModel<HomeViewModel>()

                HomeRootScreen(
                    windowSizeClass = windowSizeClass,
                    viewModel = homeViewModel,
                    navigateToDetails = { id, type ->
                        navController.navigate(AppScreen.Details(id, type))
                    },
                    navigateToSearch = {
                        navController.navigate(AppScreen.Search(it))
                    }
                )
            }

            composable<AppScreen.Profile> {
                val profileViewModel = hiltViewModel<ProfileViewmodel>()

                ProfileRootScreen(
                    windowSizeClass = windowSizeClass,
                    viewmodel = profileViewModel
                )
            }

            composable<AppScreen.Details>(
                enterTransition = {
                    fadeIn(animationSpec = tween(DEFAULT_ANIMATION_TIME)) +
                            slideInVertically(
                                animationSpec = tween(DEFAULT_ANIMATION_TIME),
                                initialOffsetY = { it })
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(DEFAULT_ANIMATION_TIME)) +
                            slideOutVertically(animationSpec = tween(DEFAULT_ANIMATION_TIME),
                                targetOffsetY = { it })
                }
            ) {
                val detailsViewModel = hiltViewModel<DetailsViewModel>()
                val payload = it.toRoute<AppScreen.Details>()

                LaunchedEffect(payload) {
                    detailsViewModel.loadDetails(payload.id, payload.type)
                }

                DetailsRootScreen(
                    viewModel = detailsViewModel,
                    windowSizeClass = windowSizeClass,
                    navigateToDetails = { id, type ->
                        navController.navigate(AppScreen.Details(id, type))
                    },
                    navigateToPerson = { id ->
                        navController.navigate(AppScreen.Person(id))
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<AppScreen.Person>(
                enterTransition = {
                    fadeIn(animationSpec = tween(DEFAULT_ANIMATION_TIME)) + slideInVertically(
                        animationSpec = tween(DEFAULT_ANIMATION_TIME)
                    )
                },
                exitTransition = {
                    fadeOut(tween(DEFAULT_ANIMATION_TIME)) + slideOutVertically(
                        tween(
                            DEFAULT_ANIMATION_TIME
                        )
                    )
                }
            ) {
                val personViewModel = hiltViewModel<PersonViewModel>()
                val payload = it.toRoute<AppScreen.Person>()

                LaunchedEffect(payload) {
                    personViewModel.loadData(payload.id)
                }

                PersonRootScreen(
                    viewModel = personViewModel,
                    windowSizeClass = windowSizeClass,
                    navigateToDetails = { id, type ->
                        navController.navigate(AppScreen.Details(id, type))
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<AppScreen.Search> {
                val type = it.toRoute<AppScreen.Search>().type
                val searchViewmodel = hiltViewModel<SearchViewModel>()

                LaunchedEffect(type) {
                    searchViewmodel.updateSearchType(type)
                }

                SearchRootScreen(
                    viewModel = searchViewmodel,
                    windowSizeClass = windowSizeClass,
                    navigateToDetails = { id, t ->
                        navController.navigate(AppScreen.Details(id, t))
                    }
                )
            }
        }

        AnimatedBottomBar(viewmodel, windowSizeClass, context)
    }
}

@Composable
private fun BoxScope.AnimatedBottomBar(
    viewmodel: CoreViewmodel,
    windowSizeClass: WindowSizeClass,
    context: Context,
) {
    AnimatedVisibility(
        viewmodel.state.isVisible &&
                windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded,
        modifier = Modifier.Companion
            .align(Alignment.BottomCenter)
            .padding(horizontal = MaterialTheme.dimens.medium3)
            .then(
                if (Settings.Secure.getInt(
                        context.contentResolver,
                        "navigation_mode",
                        0
                    ) == 2
                ) Modifier.padding(bottom = MaterialTheme.dimens.medium3)
                else Modifier
            )
            .navigationBarsPadding(),
        enter = fadeIn(animationSpec = tween(DEFAULT_ANIMATION_TIME)) +
                slideInVertically(
                    animationSpec = tween(DEFAULT_ANIMATION_TIME),
                    initialOffsetY = { it }),
        exit = fadeOut(animationSpec = tween(DEFAULT_ANIMATION_TIME)) +
                slideOutVertically(animationSpec = tween(DEFAULT_ANIMATION_TIME),
                    targetOffsetY = { it })
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