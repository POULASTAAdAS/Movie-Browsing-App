package com.poulastaa.mflix.person.network.model

import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.home.network.model.PrevMovieDto
import kotlinx.serialization.Serializable

@Serializable
data class KnownFor(
    val cast: List<KnownForItemDto>,
)
