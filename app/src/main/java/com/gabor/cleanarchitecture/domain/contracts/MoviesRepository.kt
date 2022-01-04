package com.gabor.cleanarchitecture.domain.contracts

import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO

interface MoviesRepository {
    suspend fun getMovies(language: String): MoviesDTO
}