package com.poulastaa.mflix.auth.presentation.intro

import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

sealed interface IntroUiEvent {
    data object NavigateToEmailLogIn : IntroUiEvent
    data class EmitToast(val message: UiText) : IntroUiEvent

    data object NavigateToHome: IntroUiEvent
}