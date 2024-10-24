package com.poulastaa.data.repository

import com.poulastaa.core.model.User
import com.poulastaa.core.model.dto.UserDto
import com.poulastaa.core.repository.DatabaseRepository
import com.poulastaa.core.table.UserTable
import com.poulastaa.data.dao.UserDao
import com.poulastaa.plugins.dbQuery

class DatabaseRepoImpl : DatabaseRepository {
    override suspend fun createUser(user: UserDto): Boolean {
        val userDao = dbQuery {
            UserDao.find {
                UserTable.email eq user.email
            }.singleOrNull()
        }

        if (userDao != null) return false

        dbQuery {
            UserDao.new {
                this.email = user.email
                this.sub = user.sub
                this.username = user.username
                this.profilePic = user.profilePic
                this.country = user.country
            }
        }

        return true
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        UserDao.find {
            UserTable.email eq email
        }.singleOrNull()?.let {
            User(
                email = it.email,
                userName = it.username,
                profilePic = it.profilePic ?: ""
            )
        }
    }
}