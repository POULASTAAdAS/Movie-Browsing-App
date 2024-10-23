package com.poulastaa.mflix.auth.presentation.email_login

import com.poulastaa.mflix.core.presentation.ui.UiText

sealed interface EmailLogInUiEvent {
    data class Navigate(val screen: NavigationScreen) : EmailLogInUiEvent
    data class EmitToast(val message: UiText) : EmailLogInUiEvent

    enum class NavigationScreen {
        FORGOT_PASSWORD,
        EMAIL_SIGNUP
    }
}