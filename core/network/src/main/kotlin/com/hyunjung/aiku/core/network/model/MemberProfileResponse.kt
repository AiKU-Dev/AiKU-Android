package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.ProfileBackground
import com.hyunjung.aiku.core.model.ProfileCharacter
import kotlinx.serialization.Serializable

@Serializable
data class MemberProfileResponse(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)

fun MemberProfileResponse.toModel(): MemberProfile =
    if (profileType == "IMG" && !profileImg.isNullOrBlank()) {
        MemberProfile.ImgProfile(profileImg = profileImg)
    } else {
        MemberProfile.CharProfile(
            profileCharacter = when (profileCharacter) {
                "C01" -> ProfileCharacter.C01
                "C02" -> ProfileCharacter.C02
                "C03" -> ProfileCharacter.C03
                "C04" -> ProfileCharacter.C04
                else -> ProfileCharacter.C01
            },
            profileBackground = when (profileBackground) {
                "GREEN" -> ProfileBackground.GREEN
                "YELLOW" -> ProfileBackground.YELLOW
                "PURPLE" -> ProfileBackground.PURPLE
                "GRAY" -> ProfileBackground.GRAY
                else -> ProfileBackground.GREEN
            }
        )
    }