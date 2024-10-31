package com.poulastaa.mflix.core.domain.model

data class Person(
    val id: Long,
    val name: String,
    val profilePath: String?,
    val role: String?,
    val order: Int?,
)
