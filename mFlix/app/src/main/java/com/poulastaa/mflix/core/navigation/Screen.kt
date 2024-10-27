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
    data object App : Screen
}

sealed interface AppScreen {
    @Serializable
    data object Home : AppScreen

    @Serializable
    data object Profile : AppScreen
}