package com.poulastaa.mflix.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Intro : Screen

    @Serializable
    data object EmailLogIn : Screen

    @Serializable
    data object EmailSignUp : Screen

    @Serializable
    data object ForgotPassword : Screen

    @Serializable
    data object Home : Screen
}