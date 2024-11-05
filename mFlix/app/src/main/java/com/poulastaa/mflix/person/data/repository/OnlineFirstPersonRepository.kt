package com.poulastaa.mflix.person.data.repository

import com.poulastaa.mflix.core.domain.model.PersonPayload
import com.poulastaa.mflix.core.domain.repository.person.PersonRepository
import com.poulastaa.mflix.core.domain.repository.person.RemotePersonDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class OnlineFirstPersonRepository @Inject constructor(
    private val remote: RemotePersonDataSource,
    private val scope: CoroutineScope,
) : PersonRepository {
    override suspend fun getData(personId: Long): Result<PersonPayload, DataError.Network> =
        remote.getData(personId)
}