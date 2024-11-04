package com.poulastaa.mflix.person

import com.poulastaa.mflix.core.domain.model.UiPrevItem

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
    val birthDay: String = "",
    val deathDay: String = "",
    val gender: String = "",
    val birthPlace: String = "",
    val popularity: Float = 0f,
    val role: String = "",
)