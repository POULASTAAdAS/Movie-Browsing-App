package com.poulastaa.mflix.auth.presentation.email_signup

sealed interface EmailSignUpUiAction {
    data class OnUserNameChanged(val newUserName: String) : EmailSignUpUiAction
    data class OnEmailChanged(val newEmail: String) : EmailSignUpUiAction
    data class OnPasswordChanged(val newPassword: String) : EmailSignUpUiAction
    data class OnConformPasswordChanged(val newPassword: String) : EmailSignUpUiAction

    data object OnTogglePasswordVisibilityClicked : EmailSignUpUiAction

    data object OnLogInClicked : EmailSignUpUiAction
    data object OnSubmitClicked : EmailSignUpUiAction
}