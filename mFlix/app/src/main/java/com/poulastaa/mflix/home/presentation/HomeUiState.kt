package com.poulastaa.mflix.home.presentation

import androidx.compose.runtime.Immutable
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiUser

@Immutable
data class HomeUiState(
    val isMakingApiCall: Boolean = false,
    val user: UiUser = UiUser(),

    val filterType: UiHomeFilterType = UiHomeFilterType.ALL,

    val spotLight: UiPrevItem = UiPrevItem(),
    val popularList: List<UiPrevItem> = emptyList(),
    val topRated: List<UiPrevItem> = emptyList(),
) {
    val dataLoaded =
        popularList.isNotEmpty() && topRated.isNotEmpty() && spotLight.title.isNotEmpty()
}

enum class UiHomeFilterType {
    ALL,
    MOVIE,
    TV
}