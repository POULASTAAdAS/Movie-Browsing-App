package com.poulastaa.mflix.details.presentation

import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.MovieProductionCompany

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
    productionCompany = production_companies.map { it.toUiMovieProductionCompany() }
)

fun MovieProductionCompany.toUiMovieProductionCompany() = UiMovieProductionCompany(
    id = id,
    logoPath = logo_path,
    name = name
)