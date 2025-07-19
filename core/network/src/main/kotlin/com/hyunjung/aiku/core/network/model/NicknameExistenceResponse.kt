package com.hyunjung.aiku.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NicknameExistenceResponse(
    val exist: Boolean
)