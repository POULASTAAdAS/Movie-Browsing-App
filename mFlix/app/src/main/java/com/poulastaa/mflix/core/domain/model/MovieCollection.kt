package com.poulastaa.mflix.core.domain.model

data class MovieCollection(
    val id: Long,
    val name: String,
    val overview: String,
    val poster_path: String,
    val parts: List<CollectionItem>,
)

data class CollectionItem(
    val id: Long,
    val title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val release_date: String,
    val vote_average: Double,
)
