package com.poulastaa.mflix.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PrevMovieWrapper(
    val results: List<PrevMovieDto>
)
