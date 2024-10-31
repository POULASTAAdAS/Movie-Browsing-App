package com.poulastaa.mflix.core.domain.repository.details

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.MovieCollection
import com.poulastaa.mflix.core.domain.model.MovieCredits
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface RemoteDetailsDataSource {
    suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network>

    suspend fun getMovieCollection(id: Long): Result<MovieCollection, DataError.Network>
    suspend fun getMovieCastAndCrew(id: Long): Result<MovieCredits, DataError.Network>

    suspend fun getRecommendation(list: String, type: PrevItemType = PrevItemType.MOVIE): Flow<PagingData<PrevItem>>

    suspend fun getTvShow(id: Long)
}