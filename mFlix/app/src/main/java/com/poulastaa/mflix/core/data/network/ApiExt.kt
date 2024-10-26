package com.poulastaa.mflix.core.data.network

import com.google.gson.Gson
import com.poulastaa.mflix.BuildConfig
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.Request as Req

val mediaType = "application/json".toMediaType()

suspend inline fun <reified Request : Any, reified Response : Any> OkHttpClient.post(
    route: String,
    body: Request,
    gson: Gson = Gson(),
    isApi: Boolean = true
): Result<Response, DataError.Network> {
    val url = if (isApi) constructApiRoute(route) else constructRoute(route)

    val reqBody = gson.toJson(body).toRequestBody(mediaType)
    val req = Req.Builder().url(url).post(reqBody)
        .build()

    return try {
        val response = makeCall(req)

        responseToResult<Response>(response, gson)
    } catch (e: Exception) {
        handleOtherException(e)
    }
}

suspend inline fun <reified Request : Any, reified Response : Any> OkHttpClient.put(
    route: String,
    body: Request,
    gson: Gson = Gson(),
    isApi: Boolean = true
): Result<Response, DataError.Network> {
    val url = if (isApi) constructApiRoute(route) else constructRoute(route)

    val reqBody = gson.toJson(body).toRequestBody(mediaType)
    val req = Req.Builder().url(url).put(reqBody).build()

    return try {
        val response = makeCall(req)
        responseToResult<Response>(response, gson)
    } catch (e: Exception) {
        handleOtherException(e)
    }
}

suspend inline fun <reified Response : Any> OkHttpClient.get(
    route: String,
    params: List<Pair<String, String>> = emptyList(),
    gson: Gson = Gson(),
    isApi: Boolean = true
): Result<Response, DataError.Network> {
    val urlBuilder =
        when {
            isApi -> constructApiRoute(route)
            else -> constructRoute(route)
        }.toHttpUrlOrNull()?.newBuilder()
            ?: return Result.Error(DataError.Network.UNKNOWN)

    params.forEach {
        urlBuilder.addQueryParameter(it.first, it.second)
    }

    val url = urlBuilder.build()
    val req = Req.Builder().url(url).get().build()

    return try {
        val response = makeCall(req)
        responseToResult<Response>(response, gson)
    } catch (e: Exception) {
        handleOtherException(e)
    }
}

suspend fun OkHttpClient.makeCall(request: Req): Response {
    return suspendCoroutine { continuation ->
        try {
            newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }
            })
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

suspend inline fun <reified T> responseToResult(
    response: Response,
    gson: Gson,
): Result<T, DataError.Network> = withContext(Dispatchers.IO) {
    when (response.code) {
        in 200..299 -> {
            val body = response.body!!.string()
            val obj = gson.fromJson(body, T::class.java)

            Result.Success(obj)
        }

        401 -> Result.Error(DataError.Network.UNAUTHORISED)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        409 -> Result.Error(DataError.Network.CONFLICT)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}


fun handleOtherException(exception: Exception): Result<Nothing, DataError.Network> {
    return when (exception) {
        is UnresolvedAddressException -> Result.Error(DataError.Network.NO_INTERNET)
        is CancellationException -> throw exception
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String) = "${BuildConfig.BASE_URL}$route"
fun constructApiRoute(route: String) = "${BuildConfig.API_BASE_URL}$route"