package com.poulastaa.mflix.search.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.navigation.AppScreen

sealed interface SearchUiAction {
    data object OnClear : SearchUiAction

    data object OnItemViewTypeToggle: SearchUiAction

    data class OnSearchTypeToggle(val type: AppScreen.SearchType): SearchUiAction

    data class OnSearchQueryChange(val query: String) : SearchUiAction
    data class OnItemClick(val id: Long, val type: UiPrevItemType) : SearchUiAction
}