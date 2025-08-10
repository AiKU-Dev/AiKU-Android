package com.hyunjung.aiku.core.network.di

import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorAuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorGroupRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorScheduleRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorUserRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @Binds
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSource: KtorAuthRemoteDataSource
    ): AuthRemoteDataSource

    @Binds
    abstract fun bindGroupRemoteDataSource(
        groupRemoteDataSource: KtorGroupRemoteDataSource
    ): GroupRemoteDataSource

    @Binds
    abstract fun bindScheduleRemoteDataSource(
        scheduleRemoteDataSource: KtorScheduleRemoteDataSource
    ): ScheduleRemoteDataSource

    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSource: KtorUserRemoteDataSource
    ): UserRemoteDataSource
}