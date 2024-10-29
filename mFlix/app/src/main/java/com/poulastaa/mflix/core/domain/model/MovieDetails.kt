package com.poulastaa.mflix.core.domain.model

data class MovieDetails(
    val belongs_to_collection: BelongsToCollection,
    val budget: Long,
    val homepage: String,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val production_companies: List<MovieProductionCompany>,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
)

data class BelongsToCollection(
    val id: Long,
)

data class MovieProductionCompany(
    val id: Long,
    val logo_path: String,
    val name: String,
)