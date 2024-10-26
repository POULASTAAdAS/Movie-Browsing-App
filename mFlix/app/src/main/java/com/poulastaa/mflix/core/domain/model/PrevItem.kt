package com.poulastaa.mflix.core.domain.model

data class PrevItem(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Double,
    val coverImage: String,
    val type: PrevItemType,
    val isInFavourite: Boolean = false
)
