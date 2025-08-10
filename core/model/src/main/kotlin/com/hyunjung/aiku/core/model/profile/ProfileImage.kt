package com.hyunjung.aiku.core.model.profile

sealed interface ProfileImage {

    data class Photo(
        val url: String
    ) : ProfileImage

    data class Avatar(
        val type: AvatarType,
        val backgroundColor: ProfileBackgroundColor
    ) : ProfileImage
}