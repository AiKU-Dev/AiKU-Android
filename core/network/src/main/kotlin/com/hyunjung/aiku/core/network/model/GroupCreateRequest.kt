package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupCreateRequest(
    @SerialName("groupName")
    val name: String
)
