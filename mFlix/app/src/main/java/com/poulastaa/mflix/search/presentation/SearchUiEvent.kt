package com.poulastaa.mflix.search.presentation

import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

sealed interface SearchUiEvent {
    data class NavigateToDetails(val id: Long, val type: PrevItemType) : SearchUiEvent
    data class EmitToast(val message: UiText) : SearchUiEvent
}