package com.hyunjung.aiku.core.model.profile

sealed interface MemberProfileImage {

    data class Photo(
        val url: String
    ) : MemberProfileImage

    data class Avatar(
        val type: AvatarType,
        val backgroundColor: ProfileBackgroundColor
    ) : MemberProfileImage
}