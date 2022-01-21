package com.gabor.cleanarchitecture.data.remote

import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.domain.movies.entities.GenresDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MovieDTO
import com.gabor.cleanarchitecture.domain.movies.entities.MoviesDTO
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MoviesRepository {

    override suspend fun getMovies(language: String, page: Int): MoviesDTO {
        return apiService.getMovies(language, page)
    }

    override suspend fun getGenres(language: String): GenresDTO {
        return apiService.getGenres(language)
    }

    override suspend fun getMovieDetails(language: String, id: Int): MovieDTO {
        return apiService.getMovieDetails(id, language)
    }
}