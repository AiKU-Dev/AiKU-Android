package com.hyunjung.aiku.core.model.profile

sealed interface MemberProfile {

    data class Image(
        val imageUrl: String
    ) : MemberProfile

    data class Avatar(
        val avatarCharacter: AvatarCharacter,
        val avatarBackground: AvatarBackground
    ) : MemberProfile
}