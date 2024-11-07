package com.poulastaa.mflix.core.data.network

import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val ds: DataStoreRepository,
) : Interceptor {
    private var usingMobileData: Boolean = false

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

        if (usingMobileData) return proxyReq(chain)
        else try {
            val response = chain.proceed(newReq)

            response.header("set-cookie")?.let {
                runBlocking {
                    ds.storeCookie(it.split(";")[0])
                }
            }

            return response
        } catch (e: Exception) {
            usingMobileData = true
            return proxyReq(chain)
        }
    }

    private fun proxyReq(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val newUrl = "${BuildConfig.BASE_URL}${EndPoints.Proxy.route}?url=$url"

        val proxyReq = Request.Builder()
            .addHeader(
                name = "Authorization",
                value = "Bearer ${BuildConfig.API_TOKEN}"
            )
            .url(newUrl)
            .get()
            .build()

        return chain.proceed(proxyReq)
    }
}