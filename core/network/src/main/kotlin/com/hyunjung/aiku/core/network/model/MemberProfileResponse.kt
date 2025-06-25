package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.data.model.MemberProfile
import kotlinx.serialization.Serializable

@Serializable
data class MemberProfileResponse(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)

fun MemberProfileResponse.toModel(): MemberProfile =
    MemberProfile(
        profileType = profileType,
        profileImg = profileImg,
        profileCharacter = profileCharacter,
        profileBackground = profileBackground
    )