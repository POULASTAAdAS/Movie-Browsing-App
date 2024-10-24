package com.poulastaa.core.model.dto

data class UserDto(
    val username: String,
    val email: String,
    val sub: String,
    val profilePic: String,
    val country: String,
)
