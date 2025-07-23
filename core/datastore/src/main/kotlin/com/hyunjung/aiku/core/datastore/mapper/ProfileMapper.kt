package com.hyunjung.aiku.core.datastore.mapper

import com.hyunjung.aiku.core.datastore.AvatarTypeProto
import com.hyunjung.aiku.core.datastore.ProfileBackgroundColorProto
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor

internal fun AvatarTypeProto.toModel(): AvatarType = when (this) {
    AvatarTypeProto.BOY -> AvatarType.BOY
    AvatarTypeProto.BABY -> AvatarType.BABY
    AvatarTypeProto.SCRATCH -> AvatarType.SCRATCH
    AvatarTypeProto.GIRL -> AvatarType.GIRL
    AvatarTypeProto.UNRECOGNIZED -> AvatarType.BOY
}

internal fun AvatarType.toProto(): AvatarTypeProto = when (this) {
    AvatarType.BOY -> AvatarTypeProto.BOY
    AvatarType.BABY -> AvatarTypeProto.BABY
    AvatarType.SCRATCH -> AvatarTypeProto.SCRATCH
    AvatarType.GIRL -> AvatarTypeProto.GIRL
}

internal fun ProfileBackgroundColorProto.toModel(): ProfileBackgroundColor = when (this) {
    ProfileBackgroundColorProto.GREEN -> ProfileBackgroundColor.GREEN
    ProfileBackgroundColorProto.YELLOW -> ProfileBackgroundColor.YELLOW
    ProfileBackgroundColorProto.PURPLE -> ProfileBackgroundColor.PURPLE
    ProfileBackgroundColorProto.GRAY -> ProfileBackgroundColor.GRAY
    ProfileBackgroundColorProto.UNRECOGNIZED -> ProfileBackgroundColor.GREEN
}

internal fun ProfileBackgroundColor.toProto(): ProfileBackgroundColorProto = when (this) {
    ProfileBackgroundColor.GREEN -> ProfileBackgroundColorProto.GREEN
    ProfileBackgroundColor.YELLOW -> ProfileBackgroundColorProto.YELLOW
    ProfileBackgroundColor.PURPLE -> ProfileBackgroundColorProto.PURPLE
    ProfileBackgroundColor.GRAY -> ProfileBackgroundColorProto.GRAY
}

