package com.hyunjung.aiku.core.datastore.mapper

import com.hyunjung.aiku.core.datastore.AvatarProto
import com.hyunjung.aiku.core.datastore.UserDataProto
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.ProfileImage

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

internal fun UserData.toProto(): UserDataProto {
    val builder = UserDataProto.newBuilder()
        .setId(id)
        .setKakaoId(kakaoId)
        .setPoint(point)
        .setEmail(email)
        .setNickname(nickname)

    when (val image = profileImage) {
        is ProfileImage.Avatar -> {
            builder.avatar = AvatarProto.newBuilder()
                .setType(image.type.toProto())
                .setBackgroundColor(image.backgroundColor.toProto())
                .build()
        }

        is ProfileImage.Photo -> {
            builder.url = image.url
        }
    }

    return builder.build()
}