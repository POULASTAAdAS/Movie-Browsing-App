package com.poulastaa.mflix.core.domain.repository.details

import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result

interface RemoteDetailsDataSource {
    suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network>
    suspend fun getTvShow(id: Long)
}