package com.poulastaa.mflix.details.network

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.model.BelongsToCollection
import com.poulastaa.mflix.core.domain.model.CollectionItem
import com.poulastaa.mflix.core.domain.model.Genre
import com.poulastaa.mflix.core.domain.model.MovieCollection
import com.poulastaa.mflix.core.domain.model.MovieCredits
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.MovieProductionCompany
import com.poulastaa.mflix.core.domain.model.Person
import com.poulastaa.mflix.details.network.model.CollectionDto
import com.poulastaa.mflix.details.network.model.CollectionItemDto
import com.poulastaa.mflix.details.network.model.GenreDto
import com.poulastaa.mflix.details.network.model.MovieCollectionDto
import com.poulastaa.mflix.details.network.model.MovieCreditsDto
import com.poulastaa.mflix.details.network.model.MovieDetailsDto
import com.poulastaa.mflix.details.network.model.PersonDto
import com.poulastaa.mflix.details.network.model.ProductionCompanyDto

private fun CollectionDto.toCollection() = BelongsToCollection(id)

private fun ProductionCompanyDto.toProductionCompany() = MovieProductionCompany(
    id = id,
    logo_path = BuildConfig.IMAGE_URL + logo_path,
    name = name
)

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    belongs_to_collection = this.belongs_to_collection?.toCollection() ?: BelongsToCollection(),
    budget = this.budget,
    homepage = this.homepage,
    id = this.id,
    overview = this.overview,
    poster_path = BuildConfig.IMAGE_URL + this.poster_path,
    production_companies = this.production_companies.map { it.toProductionCompany() },
    release_date = this.release_date,
    runtime = this.runtime,
    status = this.status,
    title = this.title,
    vote_average = this.vote_average,
    genreList = this.genres?.map { it.toGenre() } ?: emptyList()
)

fun MovieCollectionDto.toMovieCollection() = MovieCollection(
    id = id,
    name = name,
    overview = overview,
    poster_path = BuildConfig.IMAGE_URL + poster_path,
    parts = parts.map { it.toCollectionItem() }
)

private fun CollectionItemDto.toCollectionItem() = CollectionItem(
    id = id,
    title = title,
    overview = overview,
    poster_path = BuildConfig.IMAGE_URL + poster_path,
    media_type = media_type,
    release_date = release_date,
    vote_average = vote_average,
)

fun MovieCreditsDto.toMovieCredits() = MovieCredits(
    cast = cast.map { it.toPerson() },
    crew = crew.filterNot { it.profile_path == null }
        .distinctBy { it.profile_path }
        .map { it.toPerson() }
)

private fun PersonDto.toPerson() = Person(
    id = id,
    name = name,
    profilePath = BuildConfig.IMAGE_URL + profile_path,
    role = character ?: department,
    order = order
)

private fun GenreDto.toGenre() = Genre(
    id = id,
    type = name ?: ""
)
