package com.poulastaa.mflix.person.network

import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PersonPayload
import com.poulastaa.mflix.core.domain.repository.person.RemotePersonDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.person.network.model.KnownFor
import com.poulastaa.mflix.person.network.model.PersonDetailsDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpPersonDataSource @Inject constructor(
    private val client: OkHttpClient,
) : RemotePersonDataSource {
    override suspend fun getData(personId: Long): Result<PersonPayload, DataError.Network> =
        coroutineScope {
            val personDef = async {
                client.get<PersonDetailsDto>(
                    route = EndPoints.PersonDetails(personId).route
                )
            }

            val knownFor = async {
                client.get<KnownFor>(
                    route = EndPoints.PersonCombineCredit(personId).route
                )
            }

            val person = personDef.await()
            val known = knownFor.await()

            if (person is Result.Success && known is Result.Success) Result.Success(
                data = PersonPayload(
                    details = person.data.toPersonDetails(),
                    knownFor = known.data.cast
                        .filter { it.poster_path != null }
                        .map { it.toKnownForItem() }
                )
            )
            else person.map { PersonPayload() }
        }
}