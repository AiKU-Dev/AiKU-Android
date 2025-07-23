package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.datastore.mapper.toModel
import com.hyunjung.aiku.core.datastore.mapper.toProto
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val userdataProto: DataStore<UserDataProto>
) {

    val userData: Flow<UserData> = userdataProto.data.map { it.toModel() }

    suspend fun setEmail(email: String) {
        userdataProto.updateData { it.copy { this.email = email } }
    }

    suspend fun setNickname(nickname: String) {
        userdataProto.updateData { it.copy { this.nickname = nickname } }
    }

    suspend fun setProfile(profileImage: UserProfileImage) {
        userdataProto.updateData {
            it.copy {
                when (profileImage) {
                    is UserProfileImage.Avatar -> {
                        avatar = AvatarProto.newBuilder()
                            .setType(profileImage.type.toProto())
                            .setBackgroundColor(profileImage.backgroundColor.toProto())
                            .build()
                    }

                    is UserProfileImage.Photo -> {
                        filePath = profileImage.file.absolutePath
                    }
                }
            }
        }
    }
}