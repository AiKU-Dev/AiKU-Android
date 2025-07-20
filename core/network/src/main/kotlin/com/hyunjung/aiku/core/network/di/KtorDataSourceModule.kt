package com.hyunjung.aiku.core.network.di

import com.hyunjung.aiku.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.DefaultAuthRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.GroupRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorGroupRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.KtorScheduleRemoteDataSource
import com.hyunjung.aiku.core.network.datasource.ScheduleRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class KtorDataSourceModule {

    @Binds
    internal abstract fun bindGroupRemoteDataSource(
        groupRemoteDataSource: KtorGroupRemoteDataSource
    ): GroupRemoteDataSource

    @Binds
    internal abstract fun bindScheduleRemoteDataSource(
        scheduleRemoteDataSource: KtorScheduleRemoteDataSource
    ): ScheduleRemoteDataSource

    @Binds
    internal abstract fun bindAuthRemoteDataSource(
        authRemoteDataSource: DefaultAuthRemoteDataSource
    ): AuthRemoteDataSource
}