package com.poulastaa.data.repository

import com.poulastaa.core.model.AuthRes
import com.poulastaa.core.model.Payload
import com.poulastaa.core.model.UserAuthStatus
import com.poulastaa.core.model.dto.UserDto
import com.poulastaa.core.repository.AuthRepository
import com.poulastaa.core.repository.DatabaseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AuthRepoImpl(
    private val db: DatabaseRepository,
) : AuthRepository {
    override suspend fun auth(
        payload: Payload,
        country: String,
    ): AuthRes = coroutineScope {
        val user = async {
            db.createUser(
                user = UserDto(
                    username = payload.userName,
                    email = payload.email,
                    profilePic = payload.pictureUrl,
                    sub = payload.sub,
                    country = country
                )
            )
        }.await()

        when (user) {
            true -> AuthRes(
                status = UserAuthStatus.CREATED,
                user = db.getUserByEmail(payload.email)!!
            )

            false -> AuthRes(
                status = UserAuthStatus.CONFLICT,
                user = db.getUserByEmail(payload.email)!!
            )
        }
    }
}