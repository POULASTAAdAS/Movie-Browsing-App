package com.poulastaa.mflix.auth.presentation.intro

sealed interface IntroUiAction {
    data object OnEmailClick : IntroUiAction
    data object OnGoogleClick : IntroUiAction
}