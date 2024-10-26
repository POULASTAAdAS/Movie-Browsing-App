package com.poulastaa.plugins

import com.poulastaa.core.model.EndPoints
import com.poulastaa.core.model.UserSession
import com.poulastaa.core.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.session
import io.ktor.server.http.content.resolveResource

fun Application.configureSecurity() {
    install(Authentication) {
        session<UserSession>(Constants.SECURITY_LIST[1]) {
            validate { it }

            challenge { call.resolveResource(EndPoints.UnAuthorised.route) }
        }
    }
}
