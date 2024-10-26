package com.poulastaa.mflix.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PrevTvDto(
    val id: Long,
    val name: String,
    val overview: String,
    val vote_average: Double,
    val poster_path: String,
)
