package com.poulastaa.plugins

import com.poulastaa.core.model.UserSession
import com.poulastaa.core.repository.AuthRepository
import com.poulastaa.route.auth.auth
import com.poulastaa.route.home
import com.poulastaa.route.proxy
import io.ktor.client.HttpClient
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val auth by inject<AuthRepository>()
    val client by inject<HttpClient>()

    routing {
        sessionInterceptor()
        home()
        auth(auth)
        proxy(client)
    }
}

private fun Route.sessionInterceptor() {
    intercept(ApplicationCallPipeline.Call) {
        call.sessions.get<UserSession>()?.let {
            call.sessions.set(it)
        }
    }
}
