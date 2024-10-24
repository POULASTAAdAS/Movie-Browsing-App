package com.poulastaa.plugins

import com.poulastaa.core.model.UserSession
import com.poulastaa.core.utils.Constants
import com.poulastaa.data.repository.SessionStorageImpl
import io.ktor.server.application.*
import io.ktor.server.application.install
import io.ktor.server.sessions.*
import io.ktor.server.sessions.cookie
import io.ktor.util.*

fun Application.configureSession() {
    install(Sessions) {
        cookie<UserSession>(
            name = Constants.SESSION_NAME_GOOGLE,
            SessionStorageImpl()
        ) {
            transform(
                SessionTransportTransformerEncrypt(
                    hex(System.getenv("sessionEncryptionKey")),
                    hex(System.getenv("sessionSecretKey"))
                )
            )

            cookie.maxAgeInSeconds = DEFAULT_SESSION_MAX_AGE
        }
    }
}