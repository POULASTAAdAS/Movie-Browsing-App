package com.poulastaa.mflix.core.navigation

import android.app.Activity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poulastaa.mflix.auth.presentation.email_login.EmailLogInViewModel
import com.poulastaa.mflix.auth.presentation.email_login.EmailLoginRootScreen
import com.poulastaa.mflix.auth.presentation.email_signup.EmailSignUpRootScreen
import com.poulastaa.mflix.auth.presentation.email_signup.EmailSignUpViewModel
import com.poulastaa.mflix.auth.presentation.intro.IntroRootScreen
import com.poulastaa.mflix.auth.presentation.intro.IntroViewmodel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: Screen,
) {
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as Activity)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Intro> {
            val viewmodel = hiltViewModel<IntroViewmodel>()

            IntroRootScreen(
                windowSizeClass = windowSizeClass,
                viewmodel = viewmodel,
                navigateToEmailLogIn = {
                    navController.navigate(Screen.EmailLogIn)
                }
            )
        }

        composable<Screen.EmailLogIn>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(400)
                )
            }
        ) {
            val viewmodel = hiltViewModel<EmailLogInViewModel>()

            EmailLoginRootScreen(
                windowSizeClass = windowSizeClass,
                viewmodel = viewmodel,
                navigateToEmailSingUp = {
                    navController.navigate(Screen.EmailSignUp)
                },
                navigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword)
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.EmailSignUp>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(400)
                )
            }
        ) {
            val viewmodel = hiltViewModel<EmailSignUpViewModel>()

            EmailSignUpRootScreen(
                viewModel = viewmodel,
                windowSizeClass = windowSizeClass,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.ForgotPassword> {

        }
    }
}

