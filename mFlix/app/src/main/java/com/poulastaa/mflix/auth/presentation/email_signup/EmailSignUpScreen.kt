package com.poulastaa.mflix.auth.presentation.email_signup

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.presentation.designsystem.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.ObserveAsEvent

@Composable
fun EmailSignUpRootScreen(
    viewModel: EmailSignUpViewModel,
    windowSizeClass: WindowSizeClass,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

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

        },
        mediumContent = {

        },
        expandedContent = {

        }
    )
}