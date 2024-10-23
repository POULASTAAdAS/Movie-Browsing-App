package com.poulastaa.mflix.core.navigation

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poulastaa.mflix.auth.presentation.email_login.EmailExpandedLogInRootScreen
import com.poulastaa.mflix.auth.presentation.email_login.EmailLogInViewModel
import com.poulastaa.mflix.auth.presentation.email_login.EmailMediumLogInRootScreen
import com.poulastaa.mflix.auth.presentation.email_login.EmailSmallLogInRootScreen
import com.poulastaa.mflix.auth.presentation.intro.IntroRootExpandedScreen
import com.poulastaa.mflix.auth.presentation.intro.IntroRootMediumScreen
import com.poulastaa.mflix.auth.presentation.intro.IntroRootSmallScreen
import com.poulastaa.mflix.auth.presentation.intro.IntroViewmodel
import com.poulastaa.mflix.core.presentation.designsystem.AppScreenWindowSize

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

            AppScreenWindowSize(
                windowSizeClass = windowSizeClass,
                compactContent = {
                    IntroRootSmallScreen(
                        viewmodel = viewmodel,
                        navigateToEmailLogIn = {
                            navController.navigate(Screen.EmailLogIn)
                        }
                    )
                },
                mediumContent = {
                    IntroRootMediumScreen(
                        viewmodel = viewmodel,
                        navigateToEmailLogIn = {
                            navController.navigate(Screen.EmailLogIn)
                        }
                    )
                },
                expandedContent = {
                    IntroRootExpandedScreen(
                        viewmodel = viewmodel,
                        navigateToEmailLogIn = {
                            navController.navigate(Screen.EmailLogIn)
                        }
                    )
                }
            )
        }

        composable<Screen.EmailLogIn> {
            val viewmodel = hiltViewModel<EmailLogInViewModel>()

            AppScreenWindowSize(
                windowSizeClass = windowSizeClass,
                compactContent = {
                    EmailSmallLogInRootScreen(
                        viewModel = viewmodel,
                        navigateToEmailSingUp = {
                            navController.navigate(Screen.EmailSignUp) {
                                popUpTo(Screen.EmailLogIn) {
                                    inclusive = true
                                }
                            }
                        },
                        navigateToForgotPassword = {
                            navController.navigate(Screen.ForgotPassword)
                        },
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                },
                mediumContent = {
                    EmailMediumLogInRootScreen(
                        viewModel = viewmodel,
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
                },
                expandedContent = {
                    EmailExpandedLogInRootScreen(
                        viewModel = viewmodel,
                        navigateToEmailSingUp = {
                            navController.navigate(Screen.EmailSignUp) {
                                popUpTo(Screen.EmailLogIn) {
                                    inclusive = true
                                }
                            }
                        },
                        navigateToForgotPassword = {
                            navController.navigate(Screen.ForgotPassword)
                        },
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }

        composable<Screen.EmailSignUp> {

        }

        composable<Screen.ForgotPassword> {
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
    }
}