package com.poulastaa.mflix.auth.network.model

import com.poulastaa.mflix.core.data.model.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    val status: AuthStatusDto = AuthStatusDto.SOMETHING_WENT_WRONG,
    val user: UserDto = UserDto(),
)
