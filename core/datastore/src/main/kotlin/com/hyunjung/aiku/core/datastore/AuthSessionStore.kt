package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthSessionStore @Inject constructor(
    private val authSessionProto: DataStore<AuthSessionProto>
) {

    val accessToken = authSessionProto.data.map { it.accessToken }
    val refreshToken = authSessionProto.data.map { it.refreshToken }
    val socialType = authSessionProto.data.map {
        when (it.socialType) {
            SocialTypeProto.KAKAO -> SocialType.KAKAO
            else -> null
        }
    }

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        authSessionProto.updateData {
            it.copy {
                this.accessToken = accessToken
                this.refreshToken = refreshToken
            }
        }
    }

    suspend fun setAuthSession(
        accessToken: String,
        refreshToken: String,
        socialType: SocialType,
    ) {
        authSessionProto.updateData {
            it.copy {
                this.accessToken = accessToken
                this.refreshToken = refreshToken
                this.socialType = when (socialType) {
                    SocialType.KAKAO -> SocialTypeProto.KAKAO
                }
            }
        }
    }

    suspend fun clearAuthSession() {
        authSessionProto.updateData {
            it.copy {
                clearAccessToken()
                clearRefreshToken()
                clearSocialType()
            }
        }
    }
}