package com.gabor.cleanarchitecture.domain.contracts

import com.gabor.cleanarchitecture.domain.movies.entities.GenresDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MovieDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO

interface MoviesRepository {
    suspend fun getMovies(language: String, page: Int): MoviesDTO
    suspend fun getGenres(language: String): GenresDTO
    suspend fun getMovieDetails(language: String, id: Int): MovieDTO
}
