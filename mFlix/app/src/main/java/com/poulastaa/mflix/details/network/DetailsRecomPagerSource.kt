package com.poulastaa.mflix.details.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.utils.OtherRemoteException
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.home.network.model.PrevMovieWrapper
import com.poulastaa.mflix.home.network.toPrevItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

class DetailsRecomPagerSource @Inject constructor(
    private val client: OkHttpClient,
    private val ds: DataStoreRepository,
) : PagingSource<Int, PrevItem>() {
    private var includeAdults = true
    private var list: String = "878,28,12"
    private var type: PrevItemType = PrevItemType.MOVIE

    init {
        CoroutineScope(Dispatchers.IO).launch {
            ds.readAdult().collectLatest {
                includeAdults = it
            }
        }
    }

    fun init(list: String, type: PrevItemType) {
        this.list = list
        this.type = type
    }

    override fun getRefreshKey(state: PagingState<Int, PrevItem>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PrevItem> =
        coroutineScope {
            val page = params.key ?: 1

            val result = client.get<PrevMovieWrapper>(
                route = EndPoints.MovieRecommendation(
                    list = list,
                    page = page,
                    includeAdults = includeAdults
                ).route
            )

            if (result is Result.Success) LoadResult.Page(
                data = result.data.results.map { dto -> dto.toPrevItem() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.data.results.isEmpty()) null else page + 1
            ) else LoadResult.Error(OtherRemoteException)
        }
}