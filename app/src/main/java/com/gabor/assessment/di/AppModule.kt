package com.gabor.assessment.di

import com.gabor.assessment.data.remote.ApiService
import com.gabor.assessment.data.remote.MoviesRepositoryImpl
import com.gabor.assessment.data.remote.AuthInterceptor
import com.gabor.assessment.data.remote.SessionProvider
import com.gabor.assessment.domain.contracts.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Hilt Dependency Injection module
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
}


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideSessionProvider(): SessionProvider {
       return SessionProvider()
    }

    @Provides
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
    fun provideApiService(okHttpClient: OkHttpClient, sessionProvider: SessionProvider): ApiService {
        return Retrofit.Builder()
            .baseUrl(sessionProvider.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}