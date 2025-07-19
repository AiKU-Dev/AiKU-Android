package com.hyunjung.aiku.core.model

import java.io.File

sealed interface MemberProfile {
    data class RemoteImage(
        val imageUrl: String
    ) : MemberProfile

    data class GalleryImage(
        val file: File,
    ) : MemberProfile

    data class Character(
        val profileCharacter: ProfileCharacter,
        val profileBackground: ProfileBackground
    ) : MemberProfile
}