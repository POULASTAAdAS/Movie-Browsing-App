package com.poulastaa.mflix.home.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.home.data.mapper.toPrevItem
import com.poulastaa.mflix.home.data.mapper.toPrevPopular
import com.poulastaa.mflix.home.network.model.PopularWrapperDto
import com.poulastaa.mflix.home.network.model.PrevMovieWrapper
import com.poulastaa.mflix.home.network.model.PrevTvWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.random.Random

class OkHttpRemoteHomeDataSource @Inject constructor(
    private val client: OkHttpClient,
    private val pager: HomeMorePagerSource
) : RemoteHomeDataSource {
    override suspend fun getPopularData(): Result<List<PrevPopular>, DataError.Network> {
        val result = client.get<PopularWrapperDto>(
            route = EndPoints.Popular.route,
        )

        return result.map { list ->
            list.results.map {
                it.toPrevPopular()
            }
        }
    }

    override suspend fun getTopRatedData(): Result<List<PrevItem>, DataError.Network> =
        coroutineScope {
            val topRatedMoviesDef = async {
                client.get<PrevMovieWrapper>(
                    route = EndPoints.TopRatedMovies.route,
                )
            }

            val topRatedTvShowsDef = async {
                client.get<PrevTvWrapper>(
                    route = EndPoints.TopRatedTv.route
                )
            }

            val topRatedMovies = topRatedMoviesDef.await()
            val topRatedTvShows = topRatedTvShowsDef.await()

            when {
                topRatedMovies is Result.Success && topRatedTvShows is Result.Success -> {
                    val movieItems = topRatedMovies.data.results.map { dto -> dto.toPrevItem() }
                    val tvItems = topRatedTvShows.data.results.map { dto -> dto.toPrevItem() }

                    val list = (movieItems + tvItems).shuffled(Random(System.currentTimeMillis()))
                        .take(Random(System.currentTimeMillis()).nextInt(15, 18))

                    Result.Success(list)
                }

                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }

    override fun getPagingMore(): Flow<PagingData<PrevItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = 1,
        pagingSourceFactory = { pager }
    ).flow
}