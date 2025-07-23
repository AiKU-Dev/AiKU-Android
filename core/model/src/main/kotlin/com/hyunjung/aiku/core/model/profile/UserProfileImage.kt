package com.hyunjung.aiku.core.model.profile

import java.io.File

sealed interface UserProfileImage {

    data class Photo(
        val file: File,
    ) : UserProfileImage

    data class Avatar(
        val type: AvatarType,
        val backgroundColor: ProfileBackgroundColor
    ) : UserProfileImage
}