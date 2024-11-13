package com.poulastaa.mflix.search.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.utils.OtherRemoteException
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.search.network.model.SearchWrapperDto
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import javax.inject.Inject

class SearchPagerSource @Inject constructor(
    private val client: OkHttpClient,
    private val ds: DataStoreRepository,
) : PagingSource<Int, SearchPayload>() {
    private var includeAdults = true
    private var searchType: HomeDataType = HomeDataType.ALL
    private var query: String = ""

    fun init(
        query: String,
        searchType: HomeDataType,
    ) {
        this.query = query
        this.searchType = searchType
    }

    override fun getRefreshKey(state: PagingState<Int, SearchPayload>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchPayload> =
        coroutineScope {
            val page = params.key ?: 1

            val result = client.get<SearchWrapperDto>(
                route = EndPoints.Search(
                    page = page,
                    query = query,
                    includeAdult = includeAdults,
                    type = searchType.toSearchType().type
                ).route
            )

            // todo add upcoming

            if (result is Result.Success) {
                when (searchType) {
                    HomeDataType.ALL -> {
                        result.data.results
                            .filter { it.media_type == "movie" || it.media_type == "tv" }
                            .filter { it.name != null || it.title != null }
                            .map { it.toSearchPayload() }
                    }

                    HomeDataType.MOVIE -> {
                        result.data.results
                            .map { it.toSearchPayload() }
                    }

                    HomeDataType.TV -> {
                        result.data.results
                            .map { it.toSearchPayload() }
                    }
                }.let {
                    LoadResult.Page(
                        data = it,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                }
            } else LoadResult.Error(OtherRemoteException)
        }

    private fun HomeDataType.toSearchType() = when (this) {
        HomeDataType.ALL -> EndPoints.Search.SearchType.ALL
        HomeDataType.MOVIE -> EndPoints.Search.SearchType.MOVIE
        HomeDataType.TV -> EndPoints.Search.SearchType.TV
    }
}