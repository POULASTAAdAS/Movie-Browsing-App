package com.poulastaa.mflix.details.network

import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.repository.details.RemoteDetailsDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.details.network.model.MovieDetailsDto
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpDetailsDataSource @Inject constructor(
    private val client: OkHttpClient,
) : RemoteDetailsDataSource {
    override suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network> {
        val result = client.get<MovieDetailsDto>(
            route = EndPoints.MovieDetails(id).route
        )

        return result.map { it.toMovieDetails() }
    }

    override suspend fun getTvShow(id: Long) = Unit
}