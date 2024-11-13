package com.poulastaa.mflix.profile.presentation

import com.poulastaa.mflix.core.domain.model.PrevItemType

sealed interface ProfileUiEvent {
    data class OnItemClick(val id: Long, val type: PrevItemType) : ProfileUiEvent
    data class OnUpComingTypeClick(val type: PrevItemType) : ProfileUiEvent
    data object NavigateToSetting : ProfileUiEvent
}