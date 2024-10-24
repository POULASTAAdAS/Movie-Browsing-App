package com.poulastaa.mflix.auth.presentation.email_login

import com.poulastaa.mflix.core.presentation.designsystem.TextHolder

data class EmailLogInUiState(
    val isMakingApiCall: Boolean = false,

    val isValidEmail: Boolean = false,
    val email: TextHolder = TextHolder(),

    val isPasswordVisible: Boolean = false,
    val password: TextHolder = TextHolder(),
)
