package com.poulastaa.mflix.search.data

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import com.poulastaa.mflix.core.domain.repository.search.RemoteSearchDatasource
import com.poulastaa.mflix.core.domain.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnlineFirstSearchRepository @Inject constructor(
    private val remote: RemoteSearchDatasource,
) : SearchRepository {
    override fun searchResult(type: HomeDataType, query: String): Flow<PagingData<SearchPayload>> =
        remote.searchResult(type, query)
}