package com.poulastaa.mflix.details.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val belongs_to_collection: CollectionDto,
    val budget: Long,
    val homepage: String,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompanyDto>,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
)

@Serializable
data class CollectionDto(
    val id: Long,
)

@Serializable
data class ProductionCompanyDto(
    val id: Long,
    val logo_path: String,
    val name: String,
)