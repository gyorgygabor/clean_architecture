package com.gabor.cleanarchitecture.data.remote

import okhttp3.Interceptor
import okhttp3.HttpUrl
import okhttp3.Request

private const val KEY_NAME: String = "api_key"

/**
 * Retrofit interceptor to add the api key to each request.
 */
class AuthInterceptor(sessionProvider: SessionProvider) : Interceptor {
    private val keyValue: String = sessionProvider.API_KEY

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_NAME, keyValue)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}