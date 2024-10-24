package com.poulastaa.route.utils

import com.poulastaa.core.model.UserSession
import io.ktor.server.application.ApplicationCall
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun setSession(
    call: ApplicationCall,
    name: String,
    email: String,
) {
    call.sessions.set(
        UserSession(
            name = name,
            email = email
        )
    )
}