package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.data.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val latitude: Double,
    val longitude: Double,
    val locationName: String
)

fun LocationResponse.toModel(): Location = Location(
    latitude = latitude,
    longitude = longitude,
    locationName = locationName
)