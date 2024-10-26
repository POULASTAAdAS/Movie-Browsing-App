package com.poulastaa.mflix.core.domain.model

enum class AuthStatus {
    CREATED,
    CONFLICT,
    TOKEN_NOT_VALID,
    USER_NOT_FOUND,
    SOMETHING_WENT_WRONG
}