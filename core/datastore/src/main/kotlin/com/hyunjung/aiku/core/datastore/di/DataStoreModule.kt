package com.hyunjung.aiku.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.IO
import com.hyunjung.aiku.core.coroutine.Dispatcher
import com.hyunjung.aiku.core.coroutine.di.ApplicationScope
import com.hyunjung.aiku.core.datastore.AuthSession
import com.hyunjung.aiku.core.datastore.AuthSessionSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesAuthSessionDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        authSessionSerializer: AuthSessionSerializer,
    ): DataStore<AuthSession> =
        DataStoreFactory.create(
            serializer = authSessionSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("auth_session.pb")
        }
}