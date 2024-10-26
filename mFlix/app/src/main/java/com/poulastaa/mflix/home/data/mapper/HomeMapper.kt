package com.poulastaa.mflix.home.data.mapper

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.home.network.model.PrevPopularDto

fun PrevPopularDto.toPrevPopular() = PrevPopular(
    id = id,
    title = title ?: name ?: "",
    overview = overview,
    type = when (this.media_type) {
        "movie" -> PrevItemType.MOVIE
        else -> PrevItemType.TV_SERIES
    },
    rating = vote_average,
    coverImage = BuildConfig.IMAGE_URL + poster_path
)