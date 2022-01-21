/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabor.cleanarchitecture.domain.movies.usecases

import com.gabor.cleanarchitecture.data.remote.SessionProvider
import com.gabor.cleanarchitecture.domain.Interactor
import com.gabor.cleanarchitecture.domain.None
import com.gabor.cleanarchitecture.domain.Result
import com.gabor.cleanarchitecture.domain.Result.Companion.failure
import com.gabor.cleanarchitecture.domain.Result.Companion.success
import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.presentation.movies.MovieViewItem
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
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
