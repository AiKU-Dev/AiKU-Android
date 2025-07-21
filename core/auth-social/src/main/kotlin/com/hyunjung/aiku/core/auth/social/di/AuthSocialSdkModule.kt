package com.hyunjung.aiku.core.auth.social.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthSocialSdkModule {

    @Provides
    @Singleton
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance
}