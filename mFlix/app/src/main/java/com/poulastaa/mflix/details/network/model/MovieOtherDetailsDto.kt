package com.poulastaa.mflix.details.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieOtherDetailsDto(
    val cast: List<PersonDto>,
    val crew: List<PersonDto>,
)
