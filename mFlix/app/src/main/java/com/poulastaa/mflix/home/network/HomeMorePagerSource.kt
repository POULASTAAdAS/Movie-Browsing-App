package com.poulastaa.mflix.home.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.utils.OtherRemoteException
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.home.data.mapper.toPrevItem
import com.poulastaa.mflix.home.network.model.PrevMovieWrapper
import com.poulastaa.mflix.home.network.model.PrevTvWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.random.Random

class HomeMorePagerSource @Inject constructor(
    private val client: OkHttpClient,
) : PagingSource<Int, PrevItem>() {
    override fun getRefreshKey(state: PagingState<Int, PrevItem>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PrevItem> =
        coroutineScope {
            val page = params.key ?: 1

            val movieDef = async {
                client.get<PrevMovieWrapper>(
                    route = EndPoints.MoreMovie(page = page).route
                )
            }

            val tvDef = async {
                client.get<PrevTvWrapper>(
                    route = EndPoints.MoreTv(page = page).route
                )
            }

            val movieWrapper = movieDef.await()
            val tvWrapper = tvDef.await()

            when {
                movieWrapper is Result.Success && tvWrapper is Result.Success -> {
                    val movieItems = movieWrapper.data.results.map { dto -> dto.toPrevItem() }
                    val tvItems = tvWrapper.data.results.map { dto -> dto.toPrevItem() }

                    val list = (movieItems + tvItems).shuffled(Random(System.currentTimeMillis()))

                    LoadResult.Page(
                        data = list,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (list.isEmpty()) null else page + 1
                    )
                }

                else -> LoadResult.Error(OtherRemoteException)
            }
        }
}