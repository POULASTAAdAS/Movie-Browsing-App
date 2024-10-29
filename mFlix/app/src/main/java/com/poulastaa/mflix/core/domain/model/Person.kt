package com.poulastaa.mflix.core.domain.model

data class Person(
    val id: Long,
    val name: String,
    val profile_path: String?,
    val character: String?,
    val order: Int?,
)
