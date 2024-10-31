package com.poulastaa.mflix.home.presentation

import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType

fun PrevItem.toUiPrevItem() = UiPrevItem(
    id = id,
    title = title,
    description = description,
    rating = rating,
    imageUrl = coverImage,
    type = type.toUiPrevItemType(),
    isInFavourite = isInFavourite
)

fun UiPrevItemType.toPrevItemType() = when (this) {
    UiPrevItemType.MOVIE -> PrevItemType.MOVIE
    UiPrevItemType.TV_SHOW -> PrevItemType.TV_SERIES
}


fun PrevItemType.toUiPrevItemType() = when (this) {
    PrevItemType.MOVIE -> UiPrevItemType.MOVIE
    PrevItemType.TV_SERIES -> UiPrevItemType.TV_SHOW
}

fun UiHomeFilterType.toHomeDataType() = when (this) {
    UiHomeFilterType.ALL -> HomeDataType.ALL
    UiHomeFilterType.MOVIE -> HomeDataType.MOVIE
    UiHomeFilterType.TV -> HomeDataType.TV
}