package com.poulastaa.mflix.core.domain.repository.auth

import com.poulastaa.mflix.core.domain.model.AuthPayload
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result

interface RemoteAuthDataSource {
    suspend fun googleAuth(
        token: String,
        country: String,
    ): Result<AuthPayload, DataError.Network>
}