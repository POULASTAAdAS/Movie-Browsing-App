package com.poulastaa.core.model

import io.ktor.server.auth.*

data class UserSession(
    val name: String,
    val email: String,
) : Principal
