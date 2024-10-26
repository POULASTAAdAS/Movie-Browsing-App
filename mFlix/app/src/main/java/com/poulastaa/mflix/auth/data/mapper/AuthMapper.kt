package com.poulastaa.mflix.auth.data.mapper

import com.poulastaa.mflix.auth.network.model.AuthStatusDto
import com.poulastaa.mflix.core.data.model.UserDto
import com.poulastaa.mflix.core.domain.model.AuthStatus
import com.poulastaa.mflix.core.domain.model.LocalUser
import com.poulastaa.mflix.core.domain.model.UserPayload

fun UserPayload.toLocalUser() = LocalUser(
    name = userName,
    email = email,
    profilePic = profilePic
)

fun AuthStatusDto.toAuthStatus() = when (this) {
    AuthStatusDto.CREATED -> AuthStatus.CREATED
    AuthStatusDto.CONFLICT -> AuthStatus.CONFLICT
    AuthStatusDto.SOMETHING_WENT_WRONG -> AuthStatus.SOMETHING_WENT_WRONG
    AuthStatusDto.TOKEN_NOT_VALID -> AuthStatus.TOKEN_NOT_VALID
    AuthStatusDto.USER_NOT_FOUND -> AuthStatus.USER_NOT_FOUND
}

fun UserDto.otUserPayload() = UserPayload(
    userName = userName,
    email = email,
    profilePic = profilePic
)