package com.poulastaa.mflix.auth.presentation.email_signup

import com.poulastaa.mflix.core.presentation.designsystem.model.TextHolder

data class EmailSignUpUiState(
    val isMakingApiCall: Boolean = false,

    val isValidUserName: Boolean = false,
    val userName: TextHolder = TextHolder(),

    val isValidEmail: Boolean = false,
    val email: TextHolder = TextHolder(),

    val isValidPassword: Boolean = false,
    val passwordVisibility: Boolean = false,
    val password: TextHolder = TextHolder(),

    val areSamePassword: Boolean = false,
    val conformPassword: TextHolder = TextHolder()
)
