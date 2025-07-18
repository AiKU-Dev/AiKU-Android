package com.hyunjung.aiku.core.model

sealed interface MemberProfile {
    val profileType: ProfileType

    data class ImgProfile(
        override val profileType: ProfileType = ProfileType.IMG,
        val profileImg: String
    ) : MemberProfile

    data class CharProfile(
        override val profileType: ProfileType = ProfileType.CHAR,
        val profileCharacter: ProfileCharacter,
        val profileBackground: ProfileBackground
    ) : MemberProfile
}