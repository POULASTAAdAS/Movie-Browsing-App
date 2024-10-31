package com.poulastaa.mflix.details.network

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.MovieCollection
import com.poulastaa.mflix.core.domain.model.MovieCredits
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.repository.details.RemoteDetailsDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.details.network.model.MovieCollectionDto
import com.poulastaa.mflix.details.network.model.MovieCreditsDto
import com.poulastaa.mflix.details.network.model.MovieDetailsDto
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpDetailsDataSource @Inject constructor(
    private val client: OkHttpClient,
    private val pager: DetailsRecomPagerSource,
) : RemoteDetailsDataSource {
    override suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network> {
        val result = client.get<MovieDetailsDto>(
            route = EndPoints.MovieDetails(id).route
        )

        return result.map { it.toMovieDetails() }
    }

    override suspend fun getMovieCollection(id: Long): Result<MovieCollection, DataError.Network> {
        val result = client.get<MovieCollectionDto>(
            route = EndPoints.MovieCollectionDetails(id).route
        )

        return result.map { it.toMovieCollection() }
    }

    override suspend fun getMovieCastAndCrew(id: Long): Result<MovieCredits, DataError.Network> {
        val result = client.get<MovieCreditsDto>(
            route = EndPoints.MovieCredits(id).route
        )

        return result.map { it.toMovieCredits() }
    }

    override suspend fun getRecommendation(
        list: String,
        type: PrevItemType
    ): Flow<PagingData<PrevItem>> {
        pager.init(list,type)

        Log.d("called" , "called")

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 1,
            pagingSourceFactory = { pager }
        ).flow
    }

    override suspend fun getTvShow(id: Long) = Unit // todo
}