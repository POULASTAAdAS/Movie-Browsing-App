package com.poulastaa.mflix.core.data.model

import com.poulastaa.mflix.core.domain.model.LocalUser
import kotlinx.serialization.Serializable

@Serializable
data class LocalUserSerializable(
    val name: String,
    val email: String,
    val profile: String,
)


fun LocalUser.toUserSerializable() = LocalUserSerializable(
    name = name,
    email = email,
    profile = profilePic
)

fun LocalUserSerializable.toLocalUser() = LocalUser(
    name = name,
    email = email,
    profilePic = profile
)