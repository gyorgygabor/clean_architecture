package com.gabor.cleanarchitecture.domain.movies.usecases

import com.gabor.cleanarchitecture.domain.Interactor
import com.gabor.cleanarchitecture.domain.Result
import com.gabor.cleanarchitecture.domain.Result.Companion.failure
import com.gabor.cleanarchitecture.domain.Result.Companion.success
import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.domain.movies.entities.MovieDTO
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import javax.inject.Inject

/**
 * Obtains the movies list from the backend based on the user`s locale, convert the response to UI model.
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : Interactor<Int, MovieDTO>() {

    override suspend fun run(params: Int): Result<Throwable, MovieDTO> {
        val movies = try {
            val locale = LocaleUtils.getCurrentLocaleAsString()
            moviesRepository.getMovieDetails(locale, params)
        } catch (e: Exception) {
            // Failures are handled by the general error handler. See: ErrorHandler
            return failure(e)
        }
        return success(movies)
    }
}
