package com.poulastaa.mflix.search.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchItemDto(
    val id: Long,
    val media_type: String,
    val title: String?,
    val name: String?,
    val poster_path: String?,
    val popularity: Double?,
    val vote_average: Double?,
    val release_date: String?
)
