package com.poulastaa.route

import com.poulastaa.core.model.EndPoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.proxy(
    client: HttpClient,
) {
    route(EndPoints.Proxy.route) {
        get {
            val url = call.request.queryParameters["url"]
                ?: return@get call.respondRedirect(EndPoints.UnAuthorised.route)

            val headers =
                call.request.headers["Authorization"] ?: return@get call.respondRedirect(EndPoints.UnAuthorised.route)

            val result = client.get(url) {
                header("Authorization", headers)
            }

            call.respond(HttpStatusCode.OK, result.bodyAsText())
        }
    }
}