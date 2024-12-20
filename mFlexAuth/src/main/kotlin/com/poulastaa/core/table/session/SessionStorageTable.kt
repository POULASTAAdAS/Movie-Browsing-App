package com.poulastaa.core.table.session

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object SessionStorageTable : IntIdTable() {
    val sessionId: Column<String> = varchar("sessionId", 100).uniqueIndex()
    val value: Column<String> = varchar("value", 100)
}