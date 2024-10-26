package com.poulastaa.mflix.auth.network.repository

import com.poulastaa.mflix.auth.data.mapper.otUserPayload
import com.poulastaa.mflix.auth.data.mapper.toAuthStatus
import com.poulastaa.mflix.auth.network.model.AuthDto
import com.poulastaa.mflix.auth.network.model.GoogleAuthReq
import com.poulastaa.mflix.core.data.network.post
import com.poulastaa.mflix.core.domain.model.AuthPayload
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.repository.auth.RemoteAuthDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpAuthDataSource @Inject constructor(
    private val client: OkHttpClient
) : RemoteAuthDataSource {
    override suspend fun googleAuth(
        token: String,
        country: String
    ): Result<AuthPayload, DataError.Network> {
        val result = client.post<GoogleAuthReq, AuthDto>(
            route = EndPoints.Auth.route,
            body = GoogleAuthReq(
                token = token,
                region = country
            ),
            isApi = false
        )

        return result.map {
            AuthPayload(
                state = it.status.toAuthStatus(),
                user = it.user.otUserPayload()
            )
        }
    }
}