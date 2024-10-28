package com.poulastaa.mflix.profile.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiUser

data class ProfileUiState(
    val user: UiUser = UiUser(),

    val favourite: List<UiPrevItem> = emptyList(),
    val upcomingMovie: List<UiPrevItem> = emptyList(),
    val upcomingTv: List<UiPrevItem> = emptyList(),
) {
    val hasData = upcomingMovie.isNotEmpty() || upcomingTv.isNotEmpty()
}
