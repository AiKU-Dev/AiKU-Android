package com.hyunjung.aiku.core.model

import com.hyunjung.aiku.core.model.profile.UserProfileImage

data class UserData(
    val email: String,
    val nickname: String,
    val profileImage: UserProfileImage,
)