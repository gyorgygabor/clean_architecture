package com.gabor.cleanarchitecture.data.remote

import com.gabor.cleanarchitecture.domain.movies.entities.GenresDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MovieDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("language") language: String, @Query("page") page: Int): MoviesDTO

    @GET("/3/genre/movie/list")
    suspend fun getGenres(@Query("language") language: String): GenresDTO

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("language") language: String, @Query("append_to_response") append_to_response: String = "videos"): MovieDTO
}