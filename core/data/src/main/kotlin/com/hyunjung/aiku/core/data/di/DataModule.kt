package com.hyunjung.aiku.core.data.di

import com.hyunjung.aiku.core.data.repository.DefaultGroupRepository
import com.hyunjung.aiku.core.data.repository.GroupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsGroupRepository(
        groupRepositoryImpl: DefaultGroupRepository
    ): GroupRepository
}