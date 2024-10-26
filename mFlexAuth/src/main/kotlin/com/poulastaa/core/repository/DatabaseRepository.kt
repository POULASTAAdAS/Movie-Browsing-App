package com.poulastaa.core.repository

import com.poulastaa.core.model.User
import com.poulastaa.core.model.dto.UserDto

interface DatabaseRepository {
    suspend fun createUser(user: UserDto): Boolean
    suspend fun getUserByEmail(email: String): User?
}