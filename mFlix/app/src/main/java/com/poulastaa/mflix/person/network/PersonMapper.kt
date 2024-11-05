package com.poulastaa.mflix.person.network

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.model.KnownForItem
import com.poulastaa.mflix.core.domain.model.PersonDetails
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.person.network.model.KnownForItemDto
import com.poulastaa.mflix.person.network.model.PersonDetailsDto

fun PersonDetailsDto.toPersonDetails() = PersonDetails(
    id = id,
    name = name,
    coverImage = BuildConfig.IMAGE_URL + profile_path,
    birthDay = birthday,
    deathDay = deathday,
    gender = gender,
    birthPlace = place_of_birth,
    popularity = popularity.toFloat(),
    role = known_for_department
)

fun KnownForItemDto.toKnownForItem() = KnownForItem(
    id = id,
    coverImage = BuildConfig.IMAGE_URL + poster_path,
    type = if (media_type == "movie") PrevItemType.MOVIE else PrevItemType.TV_SERIES
)