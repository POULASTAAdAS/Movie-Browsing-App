package com.poulastaa.mflix.auth.data.repository

import com.poulastaa.mflix.auth.data.mapper.toLocalUser
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.auth.AuthRepository
import com.poulastaa.mflix.core.domain.repository.auth.RemoteAuthDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.EmptyResult
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.asEmptyDataResult
import com.poulastaa.mflix.core.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import java.net.CookieManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val cookieManager: CookieManager,
    private val ds: DataStoreRepository,
    private val scope: CoroutineScope,
    private val remote: RemoteAuthDataSource
) : AuthRepository {
    override suspend fun googleAuth(
        token: String,
        country: String
    ): EmptyResult<DataError.Network> {
        return when (val result = remote.googleAuth(token, country)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                val user = result.data.user ?: return result.asEmptyDataResult()
                ds.storeUser(user.toLocalUser())
                ds.storeSignInState(Screen.Home)

                result.asEmptyDataResult()
            }
        }
    }
}