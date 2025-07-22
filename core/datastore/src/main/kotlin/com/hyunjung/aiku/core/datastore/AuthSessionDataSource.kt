package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthSessionDataSource @Inject constructor(
    private val authSession: DataStore<AuthSession>
) {

    val accessToken = authSession.data.map { it.accessToken }
    val refreshToken = authSession.data.map { it.refreshToken }
    val socialType = authSession.data.map {
        when (it.socialType) {
            SocialTypeProto.KAKAO -> SocialType.KAKAO
            else -> null
        }
    }

    suspend fun setCredentials(
        accessToken: String,
        refreshToken: String,
        socialType: SocialType,
    ) {
        authSession.updateData {
            it.copy {
                this.accessToken = accessToken
                this.refreshToken = refreshToken
                this.socialType = when (socialType) {
                    SocialType.KAKAO -> SocialTypeProto.KAKAO
                }
            }
        }
    }

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        authSession.updateData {
            it.copy {
                this.accessToken = accessToken
                this.refreshToken = refreshToken
            }
        }
    }

    suspend fun clearCredentials() {
        authSession.updateData {
            it.copy {
                this.accessToken = ""
                this.refreshToken = ""
                this.socialType = SocialTypeProto.UNSPECIFIED
            }
        }
    }
}