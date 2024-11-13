package com.poulastaa.mflix.profile.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItemType

sealed interface ProfileUiAction {
    data object OnEditClick : ProfileUiAction

    data class OnItemClick(val id: Long, val type: UiPrevItemType) : ProfileUiAction

    data object OnSettingClick : ProfileUiAction
}