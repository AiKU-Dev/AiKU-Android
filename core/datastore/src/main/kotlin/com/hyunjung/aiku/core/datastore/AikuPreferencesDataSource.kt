package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.model.UserAuthData
import kotlinx.coroutines.flow.map

class AikuPreferencesDataSource(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userAuthData = userPreferences.data.map {
        UserAuthData(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
            socialType = when (it.socialType) {
                SocialTypeProto.KAKAO -> SocialType.KAKAO
                else -> null
            }
        )
    }

    suspend fun setCredentials(
        authTokens: AuthTokens,
        socialType: SocialType,
    ) {
        userPreferences.updateData {
            it.copy {
                this.accessToken = authTokens.accessToken
                this.refreshToken = authTokens.refreshToken
                this.socialType = when (socialType) {
                    SocialType.KAKAO -> SocialTypeProto.KAKAO
                }
            }
        }
    }

    suspend fun setTokens(authTokens: AuthTokens) {
        userPreferences.updateData {
            it.copy {
                this.accessToken = authTokens.accessToken
                this.refreshToken = authTokens.refreshToken
            }
        }
    }

    suspend fun clearCredentials() {
        userPreferences.updateData {
            it.copy {
                this.accessToken = ""
                this.refreshToken = ""
                this.socialType = SocialTypeProto.UNSPECIFIED
            }
        }
    }
}