package com.poulastaa.mflix.home.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItemType

sealed interface HomeUiAction {
    data object OnSearchClick : HomeUiAction
    data class OnItemClick(
        val id: Long,
        val type: UiPrevItemType
    ) : HomeUiAction
}