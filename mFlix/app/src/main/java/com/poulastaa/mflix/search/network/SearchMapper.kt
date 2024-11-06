package com.poulastaa.mflix.search.network

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import com.poulastaa.mflix.search.network.model.SearchItemDto

fun SearchItemDto.toSearchPayload() = SearchPayload(
    id = id,
    coverImage = BuildConfig.IMAGE_URL + poster_path,
    name = title ?: name ?: "",
    type = when (this.media_type) {
        "movie" -> PrevItemType.MOVIE
        else -> PrevItemType.TV_SERIES
    },
    rating = this.vote_average?.toFloat() ?: this.popularity?.toFloat() ?: 0f,
    releaseDate = release_date
)