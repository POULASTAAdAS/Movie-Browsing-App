package com.poulastaa

import com.poulastaa.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSession()
    configureKoin(this)
    configureDatabases()
    configureRouting()
}
