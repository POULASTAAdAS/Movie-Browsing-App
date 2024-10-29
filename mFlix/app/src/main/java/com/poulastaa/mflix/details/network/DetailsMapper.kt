package com.poulastaa.mflix.details.network

import com.poulastaa.mflix.core.domain.model.BelongsToCollection
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.MovieProductionCompany
import com.poulastaa.mflix.details.network.model.CollectionDto
import com.poulastaa.mflix.details.network.model.MovieDetailsDto
import com.poulastaa.mflix.details.network.model.ProductionCompanyDto

fun CollectionDto.toCollection() = BelongsToCollection(id)

fun ProductionCompanyDto.toProductionCompany() = MovieProductionCompany(
    id = id,
    logo_path = logo_path,
    name = name
)

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    belongs_to_collection = this.belongs_to_collection.toCollection(),
    budget = this.budget,
    homepage = this.homepage,
    id = this.id,
    overview = this.overview,
    poster_path = this.poster_path,
    production_companies = this.production_companies.map { it.toProductionCompany() },
    release_date = this.release_date,
    runtime = this.runtime,
    status = this.status,
    title = this.title,
    vote_average = this.vote_average
)