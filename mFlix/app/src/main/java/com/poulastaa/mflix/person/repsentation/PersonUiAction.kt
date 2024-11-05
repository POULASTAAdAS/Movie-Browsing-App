package com.poulastaa.mflix.person.repsentation

import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType

sealed interface PersonUiAction {
    data class OnItemClick(val id: Long, val type: UiPrevItemType) : PersonUiAction
}