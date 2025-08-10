package com.hyunjung.aiku.core.datastore.mapper

import com.hyunjung.aiku.core.datastore.UserDataProto
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileImage
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor

internal fun UserDataProto.toModel(): UserData = UserData(
    id = id,
    kakaoId = kakaoId,
    point = point,
    email = email,
    nickname = nickname,
    profileImage = when (profileImageCase) {
        UserDataProto.ProfileImageCase.AVATAR -> ProfileImage.Avatar(
            type = avatar.type.toModel(),
            backgroundColor = avatar.backgroundColor.toModel(),
        )

        UserDataProto.ProfileImageCase.URL ->
            ProfileImage.Photo(url = url)

        UserDataProto.ProfileImageCase.PROFILEIMAGE_NOT_SET -> ProfileImage.Avatar(
            type = AvatarType.BOY,
            backgroundColor = ProfileBackgroundColor.GREEN
        )
    }
)