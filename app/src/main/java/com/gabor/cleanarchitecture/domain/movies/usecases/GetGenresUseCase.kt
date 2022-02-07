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

import com.gabor.cleanarchitecture.domain.Result
import com.gabor.cleanarchitecture.domain.Result.Companion.failure
import com.gabor.cleanarchitecture.domain.Result.Companion.success
import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.domain.movies.entities.GenresDTO
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Obtains the movies list from the backend based on the user`s locale, convert the response to UI model.
 */
class GetGenresUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<Result<Throwable, GenresDTO>> = flow {
        try {
            val locale = LocaleUtils.getCurrentLocaleAsString()
            val genres = moviesRepository.getGenres(locale)
            emit(success(genres))
        } catch (e: Exception) {
            // Failures are handled by the general error handler. See: ErrorHandler
            emit(failure(e))
        }
    }
}
