package com.poulastaa.core.repository

import com.poulastaa.core.model.AuthRes
import com.poulastaa.core.model.Payload

interface AuthRepository {
    suspend fun auth(
        payload: Payload,
        country: String,
    ): AuthRes
}