package com.poulastaa.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthReq(
    val token: String,
    val region: String,
)