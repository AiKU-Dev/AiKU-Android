package com.hyunjung.aiku.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.hyunjung.aiku.core.coroutine.AikuDispatchers.IO
import com.hyunjung.aiku.core.coroutine.Dispatcher
import com.hyunjung.aiku.core.coroutine.di.ApplicationScope
import com.hyunjung.aiku.core.datastore.AuthSessionProto
import com.hyunjung.aiku.core.datastore.AuthSessionProtoSerializer
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
internal object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesAuthSessionStore(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        authSessionProtoSerializer: AuthSessionProtoSerializer,
    ): DataStore<AuthSessionProto> =
        DataStoreFactory.create(
            serializer = authSessionProtoSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("auth_session.pb")
        }
}