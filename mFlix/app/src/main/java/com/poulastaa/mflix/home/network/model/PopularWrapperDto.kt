package com.poulastaa.mflix.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PopularWrapperDto(
    val results: List<PrevPopularDto>,
)
