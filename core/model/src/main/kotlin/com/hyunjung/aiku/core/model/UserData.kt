package com.hyunjung.aiku.core.model

import com.hyunjung.aiku.core.model.profile.ProfileImage

data class UserData(
    val id: Long,
    val email: String,
    val nickname: String,
    val kakaoId: String,
    val profileImage: ProfileImage,
    val point: Int = 0,
)