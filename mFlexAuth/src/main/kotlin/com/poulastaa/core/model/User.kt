package com.poulastaa.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String = "",
    val userName: String = "",
    val profilePic: String = "",
)