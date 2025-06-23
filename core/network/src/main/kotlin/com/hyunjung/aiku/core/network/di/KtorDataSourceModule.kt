package com.hyunjung.aiku.core.network.di

import com.hyunjung.aiku.core.data.datasource.GroupDataSource
import com.hyunjung.aiku.core.network.ktor.KtorGroupDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface KtorDataSourceModule {

    @Binds
    fun bindGroupDataSource(
        groupDataSource: KtorGroupDataSourceImpl
    ): GroupDataSource
}