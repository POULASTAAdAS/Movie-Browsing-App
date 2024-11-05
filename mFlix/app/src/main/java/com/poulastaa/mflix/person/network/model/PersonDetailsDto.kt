package com.poulastaa.mflix.person.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailsDto(
    val id: Long = -1,
    val name: String = "",
    val profile_path: String = "",
    val birthday: String?,
    val deathday: String?,
    val gender: Int = -1,
    val place_of_birth: String?,
    val popularity: Double = 0.0,
    val known_for_department: String = "",
)
