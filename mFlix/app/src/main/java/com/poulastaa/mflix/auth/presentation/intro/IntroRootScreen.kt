package com.poulastaa.mflix.auth.presentation.intro

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.auth.presentation.intro.components.StartActivityForResult
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize

@Composable
fun IntroRootScreen(
    viewmodel: IntroViewmodel,
    windowSizeClass: WindowSizeClass,
    navigateToEmailLogIn: () -> Unit,
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current as Activity


    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            IntroRootSmallScreen(
                viewmodel = viewmodel,
                navigateToEmailLogIn = navigateToEmailLogIn
            )
        },
        mediumContent = {
            IntroRootMediumScreen(
                viewmodel = viewmodel,
                navigateToEmailLogIn = navigateToEmailLogIn
            )
        },
        expandedContent = {
            IntroRootExpandedScreen(
                viewmodel = viewmodel,
                navigateToEmailLogIn = navigateToEmailLogIn
            )
        }
    )

    StartActivityForResult(
        key = state.isMakingApiCall,
        activity = LocalContext.current as Activity,
        clientId = BuildConfig.GOOGLE_CLIENT_ID,
        onSuccess = {
            viewmodel.onAction(
                IntroUiAction.OnGoogleAuthSuccess(
                    token = it,
                    activity = context
                )
            )
        },
        onCanceled = {
            viewmodel.onAction(IntroUiAction.OnGoogleAuthCanceled)
        }
    )
}
