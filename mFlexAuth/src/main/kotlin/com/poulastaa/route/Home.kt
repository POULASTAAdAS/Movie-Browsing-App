package com.poulastaa.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.home() {
    route("/") {
        get {
            call.respond(
                message = "Welcome to mFlix",
                status = HttpStatusCode.OK
            )
        }
    }
}