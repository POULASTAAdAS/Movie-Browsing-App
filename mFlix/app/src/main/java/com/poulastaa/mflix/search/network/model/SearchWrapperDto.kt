package com.poulastaa.mflix.search.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchWrapperDto(
    val results: List<SearchItemDto>,
)
