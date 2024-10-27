package com.poulastaa.mflix.auth.presentation.email_login

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun EmailLoginRootScreen(
    windowSizeClass: WindowSizeClass,
    viewmodel: EmailLogInViewModel,
    navigateToEmailSingUp: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateBack: () -> Unit,
) {
    val config = LocalConfiguration.current

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            EmailSmallLogInRootScreen(
                viewModel = viewmodel,
                navigateToEmailSingUp = navigateToEmailSingUp,
                navigateToForgotPassword = navigateToForgotPassword,
                navigateBack = navigateBack
            )
        },
        mediumContent = {
            EmailMediumLogInRootScreen(
                viewModel = viewmodel,
                navigateToEmailSingUp = navigateToEmailSingUp,
                navigateToForgotPassword = navigateToForgotPassword,
                navigateBack = navigateBack
            )
        },
        expandedContent = {
            if (config.screenWidthDp > 980) EmailExpandedLargeLogInRootScreen(
                viewModel = viewmodel,
                navigateToEmailSingUp = navigateToEmailSingUp,
                navigateToForgotPassword = navigateToForgotPassword,
                navigateBack = navigateBack
            )
            else EmailExpandedSmallLogInRootScreen(
                viewModel = viewmodel,
                navigateToEmailSingUp = navigateToEmailSingUp,
                navigateToForgotPassword = navigateToForgotPassword,
                navigateBack = navigateBack
            )
        }
    )
}