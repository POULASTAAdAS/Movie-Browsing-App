package com.poulastaa.mflix.core.domain.repository

import com.poulastaa.mflix.core.domain.model.LocalUser
import com.poulastaa.mflix.core.navigation.Screen
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun storeCookie(cookie: String)
    fun readCookie(): Flow<String>

    suspend fun storeSignInState(screen: Screen)
    suspend fun readSignInState(): Screen

    suspend fun storeUser(user: LocalUser)
    suspend fun readUser(): LocalUser

    suspend fun updateAdult(state: Boolean)
    suspend fun readAdult(): Boolean
}