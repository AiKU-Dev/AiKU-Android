package com.hyunjung.aiku.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.hyunjung.aiku.core.datastore.AuthPreferences
import com.hyunjung.aiku.core.datastore.AuthPreferencesSerializer
import com.hyunjung.aiku.core.network.AikuDispatchers.IO
import com.hyunjung.aiku.core.network.Dispatcher
import com.hyunjung.aiku.core.network.di.ApplicationScope
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
    internal fun providesAuthPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        authPreferencesSerializer: AuthPreferencesSerializer,
    ): DataStore<AuthPreferences> =
        DataStoreFactory.create(
            serializer = authPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("auth_preferences.pb")
        }
}