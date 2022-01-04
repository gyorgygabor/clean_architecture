package com.gabor.cleanarchitecture.data.remote

import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("language") language: String): MoviesDTO
}