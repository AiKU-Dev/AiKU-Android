package com.hyunjung.aiku.core.coroutine.di

import com.hyunjung.aiku.core.coroutine.Dispatcher
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.IO
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.Default
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}