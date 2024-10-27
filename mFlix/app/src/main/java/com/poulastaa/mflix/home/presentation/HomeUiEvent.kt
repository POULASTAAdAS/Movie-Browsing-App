package com.poulastaa.mflix.home.presentation

import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

sealed interface HomeUiEvent {
    data class EmitToast(val message: UiText) : HomeUiEvent
    data object NavigateToLibrary : HomeUiEvent
    data object NavigateToSearch : HomeUiEvent
    data class NavigateToDetails(
        val id: Long,
        val type: PrevItemType
    ) : HomeUiEvent
}