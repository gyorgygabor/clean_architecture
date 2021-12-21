package com.gabor.assessment.domain.contracts

import com.gabor.assessment.domain.movies.entities.MoviesDTO

interface MoviesRepository {
    suspend fun getMovies(language: String): MoviesDTO
}