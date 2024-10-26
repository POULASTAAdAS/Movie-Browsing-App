package com.poulastaa.mflix.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PrevPopularDto(
    val id: Long,
    val title: String?,
    val name: String?,
    val overview: String,
    val media_type: String,
    val vote_average: Double,
    val poster_path: String,
)
