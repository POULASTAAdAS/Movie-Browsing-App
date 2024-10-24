package com.poulastaa.core.repository

import com.poulastaa.core.model.User
import com.poulastaa.core.model.dto.UserDto
import com.poulastaa.data.dao.UserDao

interface DatabaseRepository {
    suspend fun createUser(user: UserDto): Boolean
    suspend fun getUserByEmail(email: String): User?
}