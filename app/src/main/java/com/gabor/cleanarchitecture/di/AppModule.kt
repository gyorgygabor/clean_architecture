/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabor.cleanarchitecture.di

import com.gabor.cleanarchitecture.data.remote.ApiService
import com.gabor.cleanarchitecture.data.remote.AuthInterceptor
import com.gabor.cleanarchitecture.data.remote.MoviesRepositoryImpl
import com.gabor.cleanarchitecture.data.remote.SessionProvider
import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt Dependency Injection module
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionProvider(): SessionProvider {
        return SessionProvider()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sessionProvider: SessionProvider): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val authInterceptor = AuthInterceptor(sessionProvider)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient, sessionProvider: SessionProvider): ApiService {
        return Retrofit.Builder()
            .baseUrl(sessionProvider.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: ApiService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)
    }
}
