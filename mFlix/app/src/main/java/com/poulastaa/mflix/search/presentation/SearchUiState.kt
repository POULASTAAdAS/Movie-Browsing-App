package com.poulastaa.mflix.search.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.navigation.AppScreen

data class SearchUiState(
    val searchType: AppScreen.SearchType = AppScreen.SearchType.ALL,

    val viewType: UiSearchItemViewType = UiSearchItemViewType.GRID,
    val query: String = "",
    val isUpcoming: Boolean = false,
)

enum class UiSearchItemViewType {
    GRID,
    LIST
}

data class UiSearchQueryItem(
    val id: Long = -1,
    val title: String = "",
    val rating: Float = 0f,
    val coverImage: String = "",
    val type: UiPrevItemType = UiPrevItemType.MOVIE,
    val releaseDate: String = "",
)