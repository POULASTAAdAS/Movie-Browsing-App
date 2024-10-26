package com.poulastaa.mflix.core.domain.model

sealed class EndPoints(val route: String) {
    data object Auth : EndPoints(route = "/api/auth")
    data object Popular: EndPoints(route = "/trending/all/week?language=en-US")
}