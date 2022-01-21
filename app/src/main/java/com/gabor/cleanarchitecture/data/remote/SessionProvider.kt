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

import com.gabor.cleanarchitecture.utils.fromBase64

class SessionProvider {

    /**
     * The API KEY in encoded way
     */
    val API_KEY = "MWViMDE3OTk1NjcyYTY4NTYxNWU3NTRmZTViYmFiMmQ=".fromBase64()
    val BASE_URL = "https://api.themoviedb.org/"
    val STATIC_IMAGE_URL = "https://image.tmdb.org/t/p/original"
}
