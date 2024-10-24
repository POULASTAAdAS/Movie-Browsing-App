package com.poulastaa.data.dao

import com.poulastaa.core.table.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDao>(UserTable)

    var username by UserTable.userName
    var email by UserTable.email
    var sub by UserTable.sub
    var profilePic by UserTable.profilePic
    var country by UserTable.country
}