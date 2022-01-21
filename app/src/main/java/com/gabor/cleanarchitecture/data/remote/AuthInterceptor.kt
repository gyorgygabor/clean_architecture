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
package com.gabor.cleanarchitecture.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
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
