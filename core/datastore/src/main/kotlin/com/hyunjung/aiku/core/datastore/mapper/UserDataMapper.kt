package com.hyunjung.aiku.core.datastore.mapper

import com.hyunjung.aiku.core.datastore.UserDataProto
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import java.io.File

internal fun UserDataProto.toModel(): UserData = UserData(
    id = id,
    kakaoId = kakaoId,
    point = point,
    email = email,
    nickname = nickname,
    profileImage = when (profileImageCase) {
        UserDataProto.ProfileImageCase.AVATAR -> UserProfileImage.Avatar(
            type = avatar.type.toModel(),
            backgroundColor = avatar.backgroundColor.toModel(),
        )

        UserDataProto.ProfileImageCase.FILE_PATH ->
            UserProfileImage.Photo(File(filePath))

        UserDataProto.ProfileImageCase.PROFILEIMAGE_NOT_SET -> UserProfileImage.Avatar(
            type = AvatarType.BOY,
            backgroundColor = ProfileBackgroundColor.GREEN
        )
    }
)