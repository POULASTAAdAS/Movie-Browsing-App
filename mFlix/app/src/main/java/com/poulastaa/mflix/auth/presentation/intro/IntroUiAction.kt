package com.poulastaa.mflix.auth.presentation.intro

import android.app.Activity

sealed interface IntroUiAction {
    data object OnEmailClick : IntroUiAction
    data object OnGoogleAuthClick : IntroUiAction
    data class OnGoogleAuthSuccess(
        val token: String,
        val activity: Activity,
    ) : IntroUiAction

    data object OnGoogleAuthCanceled : IntroUiAction
}