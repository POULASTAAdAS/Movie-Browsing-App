package com.poulastaa.mflix.core.domain.model

data class MovieCredits(
    val cast: List<Person>,
    val crew: List<Person>,
)
