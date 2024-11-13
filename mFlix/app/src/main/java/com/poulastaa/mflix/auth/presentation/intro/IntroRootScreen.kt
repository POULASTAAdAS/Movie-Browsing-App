package com.poulastaa.mflix.auth.presentation.intro

import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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

    val context = LocalContext.current
    val activity = context as Activity

    ObserveAsEvent(viewmodel.uiEvent) {
        when (it) {
            is IntroUiEvent.EmitToast -> Toast.makeText(
                context,
                it.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            IntroUiEvent.NavigateToEmailLogIn -> navigateToEmailLogIn()
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            IntroRootSmallScreen(
                viewmodel = viewmodel,
            )
        },
        mediumContent = {
            IntroRootMediumScreen(
                viewmodel = viewmodel,
            )
        },
        expandedContent = {
            IntroRootExpandedScreen(
                viewmodel = viewmodel,
            )
        }
    )

    StartActivityForResult(
        key = state.isMakingApiCall,
        activity = activity,
        clientId = BuildConfig.GOOGLE_CLIENT_ID,
        onSuccess = {
            viewmodel.onAction(
                IntroUiAction.OnGoogleAuthSuccess(
                    token = it,
                    activity = activity
                )
            )
        },
        onCanceled = {
            viewmodel.onAction(IntroUiAction.OnGoogleAuthCanceled)
        }
    )
}
