package com.hyunjung.aiku.core.model

data class MemberProfile(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)