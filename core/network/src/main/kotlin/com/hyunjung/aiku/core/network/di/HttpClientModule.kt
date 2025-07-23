package com.hyunjung.aiku.core.network.di

import com.hyunjung.aiku.core.auth.token.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthorizedClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnauthenticatedClient

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Provides
    @Singleton
    @UnauthenticatedClient
    internal fun provideUnauthenticatedHttpClient(): HttpClient = aikuHttpClient()

    @Provides
    @Singleton
    @AuthorizedClient
    internal fun provideAuthorizedHttpClient(
        tokenManager: TokenManager
    ): HttpClient = aikuHttpClient {

        install(Auth) {
            bearer {
                loadTokens {
                    val access = tokenManager.accessToken.first()
                    val refresh = tokenManager.refreshToken.first()
                    BearerTokens(access, refresh)
                }

                refreshTokens {
                    val newTokens = tokenManager.updateTokens()
                    BearerTokens(newTokens.accessToken, newTokens.refreshToken)
                }

                sendWithoutRequest { true }
            }
        }
    }
}

private fun aikuHttpClient(
    block: HttpClientConfig<CIOEngineConfig>.() -> Unit = {}
): HttpClient = HttpClient(CIO) {

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "aiku.duckdns.org"
        }
    }

    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }

    block()
}