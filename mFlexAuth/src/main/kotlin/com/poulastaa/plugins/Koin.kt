package com.poulastaa.plugins

import com.poulastaa.di.provideRepository
import io.ktor.server.application.*
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin


fun Application.configureKoin(
    application: Application,
) {
    install(Koin) {
        modules(
            provideRepository()
        )
    }
}