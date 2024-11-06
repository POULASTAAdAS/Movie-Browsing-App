package com.poulastaa.mflix.search.presentation

import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.home.presentation.UiHomeFilterType
import com.poulastaa.mflix.home.presentation.toUiPrevItemType

fun AppScreen.SearchType.toUiHomeSearchType() = when (this) {
    AppScreen.SearchType.MOVIE -> UiHomeFilterType.MOVIE
    AppScreen.SearchType.TV_SHOW -> UiHomeFilterType.TV
    AppScreen.SearchType.ALL -> UiHomeFilterType.ALL
}

fun UiHomeFilterType.toAppSearchType() = when (this) {
    UiHomeFilterType.ALL -> AppScreen.SearchType.ALL
    UiHomeFilterType.MOVIE -> AppScreen.SearchType.MOVIE
    UiHomeFilterType.TV -> AppScreen.SearchType.TV_SHOW
}

fun AppScreen.SearchType.toHomeDataType() = when (this) {
    AppScreen.SearchType.MOVIE -> HomeDataType.MOVIE
    AppScreen.SearchType.TV_SHOW -> HomeDataType.TV
    AppScreen.SearchType.ALL -> HomeDataType.ALL
}

fun SearchPayload.toUiSearchQueryItem() = UiSearchQueryItem(
    id = id,
    title = name,
    coverImage = coverImage,
    type = this.type.toUiPrevItemType(),
    rating = rating,
    releaseDate = releaseDate ?: ""
)