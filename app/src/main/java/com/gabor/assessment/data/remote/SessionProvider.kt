package com.gabor.assessment.data.remote

import com.gabor.assessment.utils.fromBase64

class SessionProvider {

    /**
     * The API KEY in encoded way
     */
    val API_KEY = "MWViMDE3OTk1NjcyYTY4NTYxNWU3NTRmZTViYmFiMmQ=".fromBase64()
    val BASE_URL = "https://api.themoviedb.org/"
    val STATIC_IMAGE_URL = "https://image.tmdb.org/t/p/original"
}