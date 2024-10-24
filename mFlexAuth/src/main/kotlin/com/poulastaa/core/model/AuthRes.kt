package com.poulastaa.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRes(
    val status: UserAuthStatus = UserAuthStatus.USER_NOT_FOUND,
    val user: User = User(),
)
