package com.hyunjung.aiku.core.model

sealed interface MemberProfile {
    data class RemoteImage(
        val imageUrl: String
    ) : MemberProfile

    data class GalleryImage(
        val imageUri: String
    ) : MemberProfile

    data class Character(
        val profileCharacter: ProfileCharacter,
        val profileBackground: ProfileBackground
    ) : MemberProfile
}