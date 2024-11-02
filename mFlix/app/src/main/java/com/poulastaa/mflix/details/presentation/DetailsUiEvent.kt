package com.poulastaa.mflix.details.presentation

import com.poulastaa.mflix.core.domain.model.PrevItemType

sealed interface DetailsUiEvent {
    data class NavigateToDetails(val id: Long, val type: PrevItemType) : DetailsUiEvent
}