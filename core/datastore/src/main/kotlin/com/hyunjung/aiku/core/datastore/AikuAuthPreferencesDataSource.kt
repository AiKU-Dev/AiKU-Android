package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.DataStore
import com.hyunjung.aiku.core.model.SocialType
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.hyunjung.aiku.core.datastore.SocialType as SocialTypeProto

class AikuAuthPreferencesDataSource @Inject constructor(
    private val authPreferences: DataStore<AuthPreferences>
) {

    val accessToken = authPreferences.data.map { it.accessToken }
    val refreshToken = authPreferences.data.map { it.refreshToken }
    val socialType = authPreferences.data.map {
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
        authPreferences.updateData {
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
        authPreferences.updateData {
            it.copy {
                this.accessToken = accessToken
                this.refreshToken = refreshToken
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