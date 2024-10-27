package com.poulastaa.mflix.auth.presentation.email_signup

import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

sealed interface EmailSignUpEvent {
    data class EmitToast(val message: UiText) : EmailSignUpEvent
    data object NavigateToEmailLogIn : EmailSignUpEvent
}