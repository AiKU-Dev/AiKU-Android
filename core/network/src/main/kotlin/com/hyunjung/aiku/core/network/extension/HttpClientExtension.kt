package com.hyunjung.aiku.core.network.extension

import android.util.Log
import com.hyunjung.aiku.core.network.exception.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.content.PartData
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified Resource : Any, reified Response : Any> HttpClient.get(
    resource: Resource
): Response = safeCall {
    get(resource = resource)
}

suspend inline fun <reified Resource : Any, reified Response : Any> HttpClient.post(
    resource: Resource,
    body: Any
): Response = safeCall {
    post(resource) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
}

suspend inline fun <reified Resource : Any, reified Response : Any> HttpClient.submitFormWithBinaryData(
    resource: Resource,
    partsBuilder: () -> List<PartData>
): Response = safeCall {
    post(resource) {
        setBody(MultiPartFormDataContent(partsBuilder()))
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): T {
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
    return handleResponse(response)
}

suspend inline fun <reified T> handleResponse(response: HttpResponse): T =
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