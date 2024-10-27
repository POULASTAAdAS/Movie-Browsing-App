package com.poulastaa.mflix.core.domain.model

import java.time.LocalDate

sealed class EndPoints(val route: String) {
    data object Auth : EndPoints(route = "/api/auth")

    data object PopularAll : EndPoints(route = "/trending/all/week?language=en-US")
    data object PopularMovie : EndPoints(route = "/trending/movie/week?language=en-US")
    data object PopularTv : EndPoints(route = "/trending/tv/week?language=en-US")

    data object TopRatedMovies : EndPoints(route = "/movie/top_rated?language=en-US")
    data object TopRatedTv : EndPoints(route = "/tv/popular?language=en-US")

    data class MoreMovie(
        val page: Int,
        val includeAdults: Boolean,
    ) : EndPoints(route = "/discover/movie?include_adult=$includeAdults&include_video=false&language=en-US&page=$page&primary_release_year=${LocalDate.now().year}&sort_by=popularity.desc")

    data class MoreTv(
        val page: Int,
        val includeAdults: Boolean,
    ) : EndPoints(route = "/discover/tv?first_air_date_year=${LocalDate.now().year}&include_adult=$includeAdults&include_null_first_air_dates=true&language=en-US&page=$page&sort_by=popularity.desc")
}