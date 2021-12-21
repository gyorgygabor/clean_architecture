package com.gabor.assessment.domain.movies.usecases

import com.gabor.assessment.data.remote.SessionProvider
import com.gabor.assessment.domain.Result
import com.gabor.assessment.domain.Result.Companion.failure
import com.gabor.assessment.domain.Result.Companion.success
import com.gabor.assessment.domain.Interactor
import com.gabor.assessment.domain.None
import com.gabor.assessment.domain.contracts.MoviesRepository
import com.gabor.assessment.presentation.movies.MovieViewItem
import com.gabor.assessment.presentation.utils.LocaleUtils
import javax.inject.Inject

/**
 * Obtains the movies list from the backend based on the user`s locale, convert the response to UI model.
 */
class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val sessionProvider: SessionProvider
) : Interactor<None, List<MovieViewItem>>() {

    override suspend fun run(params: None): Result<Throwable, List<MovieViewItem>> {
        val movies = try {
            val locale = LocaleUtils.getCurrentLocaleAsString()
            val moviesDTO = moviesRepository.getMovies(locale)
            // map DTO to UI object
            moviesDTO.results.map {
                MovieViewItem(it.title, it.overview, sessionProvider.STATIC_IMAGE_URL + it.posterPath)
            }
        } catch (e: Exception) {
            // Failures are handled by the general error handler. See: ErrorHandler
            return failure(e)
        }
        return success(movies)
    }
}
