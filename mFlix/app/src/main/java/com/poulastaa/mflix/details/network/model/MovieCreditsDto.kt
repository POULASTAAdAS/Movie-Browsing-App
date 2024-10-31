package com.poulastaa.mflix.details.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsDto(
    val cast: List<PersonDto>,
    val crew: List<PersonDto>,
)
