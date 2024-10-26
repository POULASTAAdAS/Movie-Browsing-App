package com.poulastaa.mflix.auth.network.model

import kotlinx.serialization.Serializable

@Serializable
enum class AuthStatusDto {
    CREATED,
    CONFLICT,
    TOKEN_NOT_VALID,
    USER_NOT_FOUND,
    SOMETHING_WENT_WRONG
}