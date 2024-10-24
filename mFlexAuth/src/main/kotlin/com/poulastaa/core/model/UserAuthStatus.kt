package com.poulastaa.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserAuthStatus {
    CREATED,
    CONFLICT,
    TOKEN_NOT_VALID,
    USER_NOT_FOUND,
    SOMETHING_WENT_WRONG
}