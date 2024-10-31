package com.poulastaa.mflix.details.presentation

import com.poulastaa.mflix.core.domain.model.CollectionItem
import com.poulastaa.mflix.core.domain.model.Genre
import com.poulastaa.mflix.core.domain.model.MovieCollection
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.MovieProductionCompany
import com.poulastaa.mflix.core.domain.model.Person
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType

fun MovieDetails.toUiMovieDetails() = UiMovieDetails(
    id = id,
    title = title,
    overview = overview,
    posterPath = poster_path,
    budget = budget,
    voteAverage = vote_average,
    releaseDate = release_date,
    runtime = runtime,
    status = status,
    homepage = homepage,
    productionCompany = production_companies.map { it.toUiMovieProductionCompany() },
    genre = genreList.map { it.toUiGenre() }
)

private fun MovieProductionCompany.toUiMovieProductionCompany() = UiMovieProductionCompany(
    id = id,
    logoPath = logo_path ?: "",
    name = name
)

fun MovieCollection.toUiMovieCollection() = UiMovieCollection(
    id = id,
    name = name,
    overview = overview,
    posterPath = poster_path,
    items = parts.map { it.toUiCollectionItem() }
)

fun CollectionItem.toUiCollectionItem() = UiCollectionItem(
    id = id,
    title = title,
    overview = overview,
    posterPath = poster_path,
    mediaType = media_type,
    releaseDate = release_date,
    voteAverage = vote_average
)


fun Person.toUiPerson() = UiPerson(
    id = id,
    name = name,
    profilePath = profilePath ?: "",
    character = role ?: "",
    order = order ?: -1
)

fun UiCollectionItem.toUiPrevItem() = UiPrevItem(
    id = id,
    title = title,
    description = overview,
    imageUrl = posterPath,
    type = UiPrevItemType.MOVIE
)

private fun Genre.toUiGenre() = UiGenre(
    id = id,
    name = type
)