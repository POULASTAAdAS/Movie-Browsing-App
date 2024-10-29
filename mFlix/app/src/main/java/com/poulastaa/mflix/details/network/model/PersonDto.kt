package com.poulastaa.mflix.details.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val id: Long,
    val name: String,
    val profile_path: String?,
    val character: String?,
    val order: Int?,
)
