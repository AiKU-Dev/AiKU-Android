package com.hyunjung.aiku.core.data.model

data class MemberProfile(
    val profileType: String,
    val profileImg: String? = null,
    val profileCharacter: String? = null,
    val profileBackground: String? = null
)