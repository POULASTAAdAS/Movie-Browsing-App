package com.poulastaa.mflix.core.domain.model

data class PersonDetails(
    val id: Long = -1,
    val name: String = "",
    val coverImage: String = "",
    val birthDay: String? = null,
    val deathDay: String? = null,
    val gender: Int = -1,
    val birthPlace: String? = null,
    val popularity: Float = 0f,
    val role: String = "",
)
