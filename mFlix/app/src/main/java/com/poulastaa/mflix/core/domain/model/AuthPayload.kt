package com.poulastaa.mflix.core.domain.model

data class AuthPayload(
    val state: AuthStatus,
    val user: UserPayload? = null,
)
