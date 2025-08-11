package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.datastore.mapper.toModel
import com.hyunjung.aiku.core.datastore.mapper.toProto
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.ProfileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val userDataProto: DataStore<UserDataProto>
) {

    val userData: Flow<UserData> = userDataProto.data.map { it.toModel() }

    suspend fun setUserData(userData: UserData) {
        userDataProto.updateData { userData.toProto() }
    }

    suspend fun setEmail(email: String) {
        userDataProto.updateData { it.copy { this.email = email } }
    }

    suspend fun setNickname(nickname: String) {
        userDataProto.updateData { it.copy { this.nickname = nickname } }
    }

    suspend fun setProfileImage(profileImage: ProfileImage) {
        userDataProto.updateData {
            it.copy {
                when (profileImage) {
                    is ProfileImage.Avatar -> {
                        avatar = AvatarProto.newBuilder()
                            .setType(profileImage.type.toProto())
                            .setBackgroundColor(profileImage.backgroundColor.toProto())
                            .build()
                    }

                    is ProfileImage.Photo -> {
                        url = profileImage.url
                    }
                }
            }
        }
    }
}