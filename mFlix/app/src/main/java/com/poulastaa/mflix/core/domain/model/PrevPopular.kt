package com.poulastaa.mflix.core.domain.model

data class PrevPopular(
    val id: Long,
    val title: String,
    val overview: String,
    val type: PrevItemType,
    val rating: Double,
    val coverImage: String,
    val isInFavourite: Boolean = false,
)
