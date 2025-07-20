package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.model.AuthTokens
import com.hyunjung.aiku.core.model.SocialType
import com.hyunjung.aiku.core.datastore.SocialType as SocialTypeProto
import com.hyunjung.aiku.core.model.UserAuthData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AikuAuthPreferencesDataSource @Inject constructor(
    private val authPreferences: DataStore<AuthPreferences>
) {
    val userAuthData = authPreferences.data.map {
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
        authPreferences.updateData {
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
        authPreferences.updateData {
            it.copy {
                this.accessToken = authTokens.accessToken
                this.refreshToken = authTokens.refreshToken
            }
        }
    }

    suspend fun clearCredentials() {
        authPreferences.updateData {
            it.copy {
                this.accessToken = ""
                this.refreshToken = ""
                this.socialType = SocialTypeProto.UNSPECIFIED
            }
        }
    }
}