package com.poulastaa.core.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : LongIdTable() {
    val userName = text("username")
    val email = varchar("email", 320).uniqueIndex()
    val sub = varchar("sub", 30).uniqueIndex()
    val profilePic = varchar("profilePic", 600).nullable().default(null)
    val country = varchar("country", 320)
}