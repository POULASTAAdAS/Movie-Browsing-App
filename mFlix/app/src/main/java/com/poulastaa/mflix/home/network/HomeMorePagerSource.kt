package com.poulastaa.mflix.home.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.utils.OtherRemoteException
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.home.network.model.PrevMovieWrapper
import com.poulastaa.mflix.home.network.model.PrevTvWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.random.Random

class HomeMorePagerSource @Inject constructor(
    private val client: OkHttpClient,
    private val ds: DataStoreRepository,
) : PagingSource<Int, PrevItem>() {
    private var includeAdults = true
    private var dataType: HomeDataType = HomeDataType.ALL

    init {
        CoroutineScope(Dispatchers.IO).launch {
            ds.readCookie().collectLatest {
                includeAdults = false
            }
        }
    }

    fun setDataType(type: HomeDataType) {
        this.dataType = type
    }

    override fun getRefreshKey(state: PagingState<Int, PrevItem>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PrevItem> =
        coroutineScope {
            val page = params.key ?: 1

            when (dataType) {
                HomeDataType.ALL -> {
                    val movieDef = async {
                        client.get<PrevMovieWrapper>(
                            route = EndPoints.MoreMovie(
                                page = page,
                                includeAdults = includeAdults
                            ).route
                        )
                    }

                    val tvDef = async {
                        client.get<PrevTvWrapper>(
                            route = EndPoints.MoreTv(
                                page = page,
                                includeAdults = includeAdults
                            ).route
                        )
                    }

                    val movieWrapper = movieDef.await()
                    val tvWrapper = tvDef.await()

                    if (movieWrapper is Result.Success && tvWrapper is Result.Success) {
                        val movieItems = movieWrapper.data.results.map { dto -> dto.toPrevItem() }
                        val tvItems = tvWrapper.data.results.map { dto -> dto.toPrevItem() }

                        val list =
                            (movieItems + tvItems).shuffled(Random(System.currentTimeMillis()))

                        LoadResult.Page(
                            data = list,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (list.isEmpty()) null else page + 1
                        )
                    } else if (movieWrapper is Result.Success) LoadResult.Page(
                        data = movieWrapper.data.results.map { dto -> dto.toPrevItem() },
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (movieWrapper.data.results.isEmpty()) null else page + 1
                    )
                    else if (tvWrapper is Result.Success) LoadResult.Page(
                        data = tvWrapper.data.results.map { dto -> dto.toPrevItem() },
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (tvWrapper.data.results.isEmpty()) null else page + 1
                    )
                    else LoadResult.Error(OtherRemoteException)
                }

                HomeDataType.MOVIE -> {
                    val movieWrapper = client.get<PrevMovieWrapper>(
                        route = EndPoints.MoreMovie(
                            page = page,
                            includeAdults = includeAdults
                        ).route
                    )


                    if (movieWrapper is Result.Success) LoadResult.Page(
                        data = movieWrapper.data.results.map { dto -> dto.toPrevItem() },
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (movieWrapper.data.results.isEmpty()) null else page + 1
                    )
                    else LoadResult.Error(OtherRemoteException)
                }

                HomeDataType.TV -> {
                    val tvWrapper = client.get<PrevTvWrapper>(
                        route = EndPoints.MoreTv(
                            page = page,
                            includeAdults = includeAdults
                        ).route
                    )


                    if (tvWrapper is Result.Success) LoadResult.Page(
                        data = tvWrapper.data.results.map { dto -> dto.toPrevItem() },
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (tvWrapper.data.results.isEmpty()) null else page + 1
                    )
                    else LoadResult.Error(OtherRemoteException)
                }
            }
        }
}