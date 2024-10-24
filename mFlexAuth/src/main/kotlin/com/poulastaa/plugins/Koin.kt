package com.poulastaa.plugins

import io.ktor.server.application.*
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin


fun Application.configureKoin(
    application: Application,
) {
    install(Koin) {

    }
}