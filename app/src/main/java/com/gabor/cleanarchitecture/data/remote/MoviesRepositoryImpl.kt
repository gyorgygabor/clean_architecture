package com.gabor.cleanarchitecture.data.remote

import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MoviesRepository {

    override suspend fun getMovies(language: String): MoviesDTO {
        return apiService.getMovies(language)
    }
}