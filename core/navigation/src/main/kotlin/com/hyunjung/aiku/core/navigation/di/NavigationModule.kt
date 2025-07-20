package com.hyunjung.aiku.core.navigation.di

import com.hyunjung.aiku.core.navigation.AikuComposeNavigator
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.AppComposeNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

    @Binds
    @Singleton
    internal abstract fun provideComposeNavigator(
        aikuComposeNavigator: AikuComposeNavigator,
    ): AppComposeNavigator<AikuRoute>
}