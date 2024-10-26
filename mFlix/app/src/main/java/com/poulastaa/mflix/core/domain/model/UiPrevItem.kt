package com.poulastaa.mflix.core.domain.model

data class UiPrevItem(
    val id: Long = -1,
    val title: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val isInFavourite: Boolean = false,
    val imageUrl: String = "",
    val type: UiPrevItemType = UiPrevItemType.MOVIE
)


enum class UiPrevItemType {
    MOVIE,
    TV_SHOW
}
