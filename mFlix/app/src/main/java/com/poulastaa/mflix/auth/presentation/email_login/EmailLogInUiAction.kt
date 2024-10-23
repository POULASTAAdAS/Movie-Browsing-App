package com.poulastaa.mflix.auth.presentation.email_login

sealed interface EmailLogInUiAction {
    data class EmailChanged(val newEmail: String) : EmailLogInUiAction
    data class PasswordChanged(val newPassword: String) : EmailLogInUiAction

    data object OnForgotPasswordClick : EmailLogInUiAction
    data object OnEmailSingUpClick : EmailLogInUiAction

    data object OnPasswordVisibilityChange : EmailLogInUiAction

    data object OnSubmitClick : EmailLogInUiAction
}