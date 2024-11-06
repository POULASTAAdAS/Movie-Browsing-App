package com.poulastaa.mflix.search.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import com.poulastaa.mflix.core.domain.repository.search.RemoteSearchDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class OkHttpSearchDataSource @Inject constructor(
    private val page: SearchPagerSource,
) : RemoteSearchDatasource {
    override fun searchResult(
        type: HomeDataType,
        query: String,
    ): Flow<PagingData<SearchPayload>> {
        if (query.isEmpty()) return emptyFlow()

        page.init(query, type)

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 1,
            pagingSourceFactory = { page }
        ).flow
    }
}