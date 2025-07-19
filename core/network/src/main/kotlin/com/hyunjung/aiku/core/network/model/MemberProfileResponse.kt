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
        MemberProfile.RemoteImage(imageUrl = profileImg)
    } else {
        MemberProfile.Character(
            profileCharacter = when (profileCharacter) {
                "C01" -> ProfileCharacter.BOY
                "C02" -> ProfileCharacter.BABY
                "C03" -> ProfileCharacter.SCRATCH
                "C04" -> ProfileCharacter.GIRL
                else -> ProfileCharacter.BOY
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