package com.poulastaa.mflix.auth.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthReq(
    val token: String,
    val region: String,
)
