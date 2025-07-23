package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import kotlinx.serialization.Serializable

@Serializable
data class MemberProfileResponse(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)

fun MemberProfileResponse.toModel(): MemberProfileImage =
    if (profileType == "IMG" && !profileImg.isNullOrBlank()) {
        MemberProfileImage.Photo(url = profileImg)
    } else {
        MemberProfileImage.Avatar(
            type = when (profileCharacter) {
                "C01" -> AvatarType.BOY
                "C02" -> AvatarType.BABY
                "C03" -> AvatarType.SCRATCH
                "C04" -> AvatarType.GIRL
                else -> AvatarType.BOY
            },
            backgroundColor = when (profileBackground) {
                "GREEN" -> ProfileBackgroundColor.GREEN
                "YELLOW" -> ProfileBackgroundColor.YELLOW
                "PURPLE" -> ProfileBackgroundColor.PURPLE
                "GRAY" -> ProfileBackgroundColor.GRAY
                else -> ProfileBackgroundColor.GREEN
            }
        )
    }