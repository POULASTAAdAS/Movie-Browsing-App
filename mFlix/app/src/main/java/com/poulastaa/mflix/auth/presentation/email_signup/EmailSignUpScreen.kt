package com.poulastaa.mflix.auth.presentation.email_signup

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun EmailSignUpRootScreen(
    viewModel: EmailSignUpViewModel,
    windowSizeClass: WindowSizeClass,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val config = LocalConfiguration.current

    ObserveAsEvent(viewModel.uiEvent) {
        when (it) {
            is EmailSignUpEvent.EmitToast -> Toast.makeText(
                context,
                it.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            EmailSignUpEvent.NavigateToEmailLogIn -> navigateBack()
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            EmailSignUpSmallScreen(
                state = state,
                onAction = viewModel::onAction
            )
        },
        mediumContent = {
            EmailSignUpMediumScreen(
                state = state,
                onAction = viewModel::onAction
            )
        },
        expandedContent = {
            if (config.screenWidthDp > 980) EmailSignUpExpandedLargeScreen(
                state = state,
                onAction = viewModel::onAction
            )
            else EmailSignUpExpandedSmallScreen(
                state = state,
                onAction = viewModel::onAction
            )
        }
    )
}