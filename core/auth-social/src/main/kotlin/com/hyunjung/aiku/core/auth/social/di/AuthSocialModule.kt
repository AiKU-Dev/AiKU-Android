package com.hyunjung.aiku.core.auth.social.di

import com.hyunjung.aiku.core.auth.social.SocialAuthManager
import com.hyunjung.aiku.core.auth.social.kakao.KakaoAuthManager
import com.hyunjung.aiku.core.model.SocialType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthSocialModule {

    @Binds
    @SocialAuth(SocialType.KAKAO)
    internal abstract fun bindKakaoAuthDataSource(
        kakaoAuthManager: KakaoAuthManager
    ): SocialAuthManager
}