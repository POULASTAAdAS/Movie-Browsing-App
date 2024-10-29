package com.poulastaa.mflix.details.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieCollectionDto(
    val id: Long,
    val name: String,
    val overview: String,
    val poster_path: String,
    val parts: List<CollectionItemDto>,
)

@Serializable
data class CollectionItemDto(
    val id: Long,
    val title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val release_date: String,
    val vote_average: Double,
)
