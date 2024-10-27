package com.poulastaa.mflix.core.domain.repository.auth

import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.EmptyResult

interface AuthRepository {
    suspend fun googleAuth(
        token: String,
        country: String,
    ): EmptyResult<DataError.Network>
}