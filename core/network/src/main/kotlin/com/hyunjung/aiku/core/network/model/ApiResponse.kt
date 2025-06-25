package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val requestId: String,
    val result: T
)