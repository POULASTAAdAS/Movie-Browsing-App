package com.poulastaa.mflix.person.repsentation

import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.person.data.model.UiGenderType

data class PersonUiState(
    val person: UiPerson = UiPerson(),
    val knownFor: List<UiPrevItem> = emptyList(),
) {
    val isDataLoaded = person.id != -1L && knownFor.isNotEmpty()
}

data class UiPerson(
    val id: Long = -1,
    val name: String = "",
    val coverImage: String = "",
    val birthDay: String? = null,
    val deathDay: String? = null,
    val gender: UiGenderType = UiGenderType.MALE,
    val birthPlace: String? = null,
    val popularity: Float = 0f,
    val role: String = "",
)