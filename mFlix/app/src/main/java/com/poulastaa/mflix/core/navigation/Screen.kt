package com.poulastaa.mflix.core.navigation

import com.poulastaa.mflix.core.domain.model.PrevItemType
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

    @Serializable
    data class Details(
        val id: Long,
        val type: PrevItemType,
    ) : AppScreen

    @Serializable
    data class Person(
        val id: Long,
    ) : AppScreen

    @Serializable
    data class Search(val type: SearchType) : AppScreen

    enum class SearchType {
        MOVIE,
        TV_SHOW
    }
}