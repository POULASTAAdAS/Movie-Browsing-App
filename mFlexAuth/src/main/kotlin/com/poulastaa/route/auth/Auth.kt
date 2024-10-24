package com.poulastaa.route.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.poulastaa.core.model.AuthReq
import com.poulastaa.core.model.AuthRes
import com.poulastaa.core.model.EndPoints
import com.poulastaa.core.model.Payload
import com.poulastaa.core.model.UserAuthStatus
import com.poulastaa.core.repository.AuthRepository
import com.poulastaa.route.utils.setSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.auth(
    service: AuthRepository,
) {
    route(EndPoints.Auth.route) {
        post {
            val req = call.receiveNullable<AuthReq>() ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

            val token = req.verifyTokenId() ?: return@post call.respond(
                message = AuthRes(
                    status = UserAuthStatus.TOKEN_NOT_VALID
                ),
                status = HttpStatusCode.Unauthorized
            )

            val payload = try {
                token.toPayload()
            } catch (_: Exception) {
                return@post call.respond(
                    message = AuthRes(
                        status = UserAuthStatus.SOMETHING_WENT_WRONG,
                    ),
                    status = HttpStatusCode.InternalServerError
                )
            }

            val response = service.auth(payload, req.region)

            when (response.status) {
                UserAuthStatus.CREATED,
                UserAuthStatus.CONFLICT,
                    -> {
                    setSession(
                        call = call,
                        name = payload.userName,
                        email = payload.email
                    )

                    call.respond(
                        message = response,
                        status = HttpStatusCode.OK
                    )
                }

                UserAuthStatus.USER_NOT_FOUND -> {
                    call.respond(
                        message = response,
                        status = HttpStatusCode.NotFound
                    )
                }

                else -> {
                    call.respond(
                        message = response,
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }
        }
    }
}

private fun AuthReq.verifyTokenId(): GoogleIdToken? = try {
    GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf(System.getenv("clientId")))
        .setIssuer(System.getenv("ISSUER"))
        .build()
        .verify(this.token)
} catch (_: Exception) {
    null
}

private fun GoogleIdToken.toPayload() = Payload(
    sub = this.payload["sub"].toString(),
    userName = this.payload["name"].toString(),
    email = this.payload["email"].toString(),
    pictureUrl = this.payload["picture"].toString()
)

