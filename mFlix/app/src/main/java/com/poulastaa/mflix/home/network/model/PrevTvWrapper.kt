package com.poulastaa.mflix.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PrevTvWrapper(
    val results: List<PrevTvDto>
)
