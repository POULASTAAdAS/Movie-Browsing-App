package com.poulastaa.mflix.core.domain.model

data class SearchPayload(
    val id: Long = -1,
    val name: String = "",
    val coverImage: String = "",
    val type: PrevItemType = PrevItemType.MOVIE,
    val rating: Float = 0f,
    val releaseDate: String?
)
