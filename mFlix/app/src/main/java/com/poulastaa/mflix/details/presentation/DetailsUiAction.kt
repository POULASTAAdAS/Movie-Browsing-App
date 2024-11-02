package com.poulastaa.mflix.details.presentation

import com.poulastaa.mflix.core.domain.model.UiPrevItemType

sealed interface DetailsUiAction {
    data class OnCrewMemberClick(val id: Long) : DetailsUiAction
    data class OnCastMemberClick(val id: Long) : DetailsUiAction

    data object OnCastMemberDetailsClick : DetailsUiAction
    data object OnCrewMemberDetailsClick : DetailsUiAction

    data class OnItemClick(val id: Long,val type: UiPrevItemType) : DetailsUiAction
}