package com.poulastaa.mflix.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String = "",
    val userName: String = "",
    val profilePic: String = "",
)
