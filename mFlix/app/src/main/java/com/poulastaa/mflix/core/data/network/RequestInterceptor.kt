package com.poulastaa.mflix.core.data.network

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val ds: DataStoreRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldReq = chain.request()

        val cookie = runBlocking {
            ds.readCookie().first()
        }
        val token = BuildConfig.API_TOKEN

        val newReq = oldReq.newBuilder()
            .addHeader(
                name = "Cookie",
                value = cookie
            )
            .addHeader(
                name = "Authorization",
                value = "Bearer $token"
            ).build()

        val response = chain.proceed(newReq)

        response.header("set-cookie")?.let {
            runBlocking {
                ds.storeCookie(it.split(";")[0])
            }
        }

        return response
    }
}