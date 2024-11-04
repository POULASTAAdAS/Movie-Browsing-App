package com.poulastaa.mflix.person

import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

sealed interface PersonUiEvent {
    data class NavigateToDetails(val id: Long, val type: PrevItemType) : PersonUiEvent
    data class EmitToast(val message: UiText) : PersonUiEvent
}