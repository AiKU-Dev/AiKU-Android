package com.hyunjung.aiku.core.data.di

import com.hyunjung.aiku.core.data.repository.DefaultGroupRepository
import com.hyunjung.aiku.core.data.repository.DefaultScheduleRepository
import com.hyunjung.aiku.core.data.repository.DefaultUserAuthRepository
import com.hyunjung.aiku.core.data.token.DefaultTokenManager
import com.hyunjung.aiku.core.auth.TokenManager
import com.hyunjung.aiku.core.domain.repository.GroupRepository
import com.hyunjung.aiku.core.domain.repository.ScheduleRepository
import com.hyunjung.aiku.core.domain.repository.UserAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsGroupRepository(
        groupRepository: DefaultGroupRepository
    ): GroupRepository

    @Binds
    internal abstract fun bindsScheduleRepository(
        scheduleRepository: DefaultScheduleRepository
    ): ScheduleRepository

    @Binds
    internal abstract fun bindsUserAuthRepository(
        userAuthRepository: DefaultUserAuthRepository
    ): UserAuthRepository

    @Binds
    internal abstract fun bindsTokenManager(
        tokenManager: DefaultTokenManager
    ): TokenManager
}