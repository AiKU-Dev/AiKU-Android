package com.hyunjung.aiku.core.network.extension

import android.util.Log
import com.hyunjung.aiku.core.network.exception.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T : Any> HttpClient.get(
    resource: T,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = executeNetworkCall {
    get(resource = resource, builder = builder)
}

suspend inline fun <reified T : Any> HttpClient.post(
    resource: T,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = executeNetworkCall {
    post(resource = resource, builder = builder)
}

suspend inline fun <reified T : Any> HttpClient.postJson(
    resource: T,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = executeNetworkCall {
    post(resource) {
        contentType(ContentType.Application.Json)
        builder()
    }
}

suspend inline fun <reified T> executeNetworkCall(execute: () -> HttpResponse): T {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        throw NetworkException.NoInternet(e)
    } catch (e: SerializationException) {
        throw NetworkException.Serialization(e)
    } catch (e: SocketTimeoutException) {
        throw NetworkException.RequestTimeout(e)
    } catch (e: CancellationException) {
        throw NetworkException.Cancellation(e)
    } catch (e: IOException) {
        throw NetworkException.NoInternet(e)
    } catch (e: Exception) {
        throw NetworkException.Unknown(e)
    }

    Log.d("testaaa", "HTTP 응답: $response")
    return parseResponseBody(response)
}

suspend inline fun <reified T> parseResponseBody(response: HttpResponse): T =
    when (response.status.value) {
        in 200..299 -> response.body<T>()
        401 -> throw NetworkException.Unauthorized()
        403 -> throw NetworkException.Forbidden()
        404 -> throw NetworkException.NotFound()
        408 -> throw NetworkException.RequestTimeout()
        409 -> throw NetworkException.Conflict()
        413 -> throw NetworkException.PayloadTooLarge()
        429 -> throw NetworkException.TooManyRequests()
        in 500..599 -> throw NetworkException.ServerError()
        else -> throw NetworkException.Unknown()
    }