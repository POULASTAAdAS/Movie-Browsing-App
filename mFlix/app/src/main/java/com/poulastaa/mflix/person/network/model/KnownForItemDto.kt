package com.poulastaa.mflix.person.network.model

import kotlinx.serialization.Serializable

@Serializable
data class KnownForItemDto(
    val id: Long,
    val poster_path: String?,
    val media_type: String,
)
