package com.hyunjung.aiku.core.model.profile

import java.io.File

sealed interface PendingProfileImage {

    data class Photo(
        val file: File,
    ) : PendingProfileImage

    data class Avatar(
        val type: AvatarType,
        val backgroundColor: ProfileBackgroundColor
    ) : PendingProfileImage
}