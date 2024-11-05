package com.poulastaa.mflix.person.repsentation

import com.poulastaa.mflix.core.domain.model.KnownForItem
import com.poulastaa.mflix.core.domain.model.PersonDetails
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.home.presentation.toUiPrevItemType
import com.poulastaa.mflix.person.data.model.UiGenderType

fun PersonDetails.toUiPerson() = UiPerson(
    id = id,
    name = name,
    coverImage = coverImage,
    birthDay = birthDay,
    deathDay = deathDay,
    gender = if (gender == 1) UiGenderType.FEMALE else if (gender == 2) UiGenderType.MALE else UiGenderType.OTHER,
    birthPlace = birthPlace,
    popularity = popularity,
    role = role
)

fun KnownForItem.toUiPrevItem() = UiPrevItem(
    id = id,
    imageUrl = coverImage,
    type = type.toUiPrevItemType()
)