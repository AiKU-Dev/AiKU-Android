package com.hyunjung.aiku.core.network.model

import com.hyunjung.aiku.core.model.profile.AvatarBackground
import com.hyunjung.aiku.core.model.profile.AvatarCharacter
import com.hyunjung.aiku.core.model.profile.MemberProfile
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
        MemberProfile.Image(imageUrl = profileImg)
    } else {
        MemberProfile.Avatar(
            avatarCharacter = when (profileCharacter) {
                "C01" -> AvatarCharacter.BOY
                "C02" -> AvatarCharacter.BABY
                "C03" -> AvatarCharacter.SCRATCH
                "C04" -> AvatarCharacter.GIRL
                else -> AvatarCharacter.BOY
            },
            avatarBackground = when (profileBackground) {
                "GREEN" -> AvatarBackground.GREEN
                "YELLOW" -> AvatarBackground.YELLOW
                "PURPLE" -> AvatarBackground.PURPLE
                "GRAY" -> AvatarBackground.GRAY
                else -> AvatarBackground.GREEN
            }
        )
    }