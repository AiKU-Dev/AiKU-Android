package com.hyunjung.aiku.core.model

import com.hyunjung.aiku.core.model.profile.UserProfileImage

data class UserData(
    val id: Long,
    val email: String,
    val nickname: String,
    val kakaoId: String,
    val profileImage: UserProfileImage,
    val point: Int = 0,
)