package com.hyunjung.aiku.core.network

sealed class NetworkException(message: String? = null) : Exception(message) {
    object RequestTimeout : NetworkException("Request Timeout") {
        private fun readResolve(): Any = RequestTimeout
    }

    object Unauthorized : NetworkException("Unauthorized") {
        private fun readResolve(): Any = Unauthorized
    }

    object Conflict : NetworkException("Conflict") {
        private fun readResolve(): Any = Conflict
    }

    object TooManyRequests : NetworkException("Too Many Requests") {
        private fun readResolve(): Any = TooManyRequests
    }

    object NoInternet : NetworkException("No Internet Connection") {
        private fun readResolve(): Any = NoInternet
    }

    object NotFound : NetworkException("Not Found") {
        private fun readResolve(): Any = NotFound
    }

    object PayloadTooLarge : NetworkException("Payload Too Large") {
        private fun readResolve(): Any = PayloadTooLarge
    }

    object ServerError : NetworkException("Server Error") {
        private fun readResolve(): Any = ServerError
    }

    object Serialization : NetworkException("Serialization Error") {
        private fun readResolve(): Any = Serialization
    }

    object Unknown : NetworkException("Unknown Error") {
        private fun readResolve(): Any = Unknown
    }
}