package com.hyunjung.aiku.core.model.profile

import java.io.File

sealed interface UserProfile {

    data class Image(
        val file: File,
    ) : UserProfile

    data class Avatar(
        val avatarCharacter: AvatarCharacter,
        val avatarBackground: AvatarBackground
    ) : UserProfile
}