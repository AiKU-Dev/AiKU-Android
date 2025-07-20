package com.hyunjung.aiku.core.network.exception

sealed class NetworkException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class RequestTimeout(cause: Throwable? = null) :
        NetworkException("Request Timeout", cause)

    class Unauthorized(cause: Throwable? = null) :
        NetworkException("Unauthorized", cause)

    class Forbidden(cause: Throwable? = null) :
        NetworkException("Forbidden", cause)

    class Conflict(cause: Throwable? = null) :
        NetworkException("Conflict", cause)

    class TooManyRequests(cause: Throwable? = null) :
        NetworkException("Too Many Requests", cause)

    class NoInternet(cause: Throwable? = null) :
        NetworkException("No Internet Connection", cause)

    class NotFound(cause: Throwable? = null) :
        NetworkException("Not Found", cause)

    class PayloadTooLarge(cause: Throwable? = null) :
        NetworkException("Payload Too Large", cause)

    class ServerError(cause: Throwable? = null) :
        NetworkException("Server Error", cause)

    class Serialization(cause: Throwable? = null) :
        NetworkException("Serialization Error", cause)

    class Unknown(cause: Throwable? = null) :
        NetworkException("Unknown Error", cause)

    class Cancellation(cause: Throwable? = null) :
        NetworkException("Cancelled by Coroutine", cause)
}