package com.poulastaa.mflix.person

import com.poulastaa.mflix.core.domain.model.PrevItemType

sealed interface PersonUiAction {
    data class OnItemClick(val id: Long, val type: PrevItemType) : PersonUiAction
}