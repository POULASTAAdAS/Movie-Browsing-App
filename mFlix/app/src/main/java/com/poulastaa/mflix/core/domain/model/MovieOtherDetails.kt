package com.poulastaa.mflix.core.domain.model

data class MovieOtherDetails(
    val cast: List<Person>,
    val crew: List<Person>,
)