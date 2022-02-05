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
package com.gabor.cleanarchitecture.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabor.cleanarchitecture.data.remote.SessionProvider
import com.gabor.cleanarchitecture.domain.movies.entities.MovieDTO
import com.gabor.cleanarchitecture.domain.movies.usecases.GetMovieDetailsUseCase
import com.gabor.cleanarchitecture.domain.movies.usecases.GetMoviesUseCase
import com.gabor.cleanarchitecture.presentation.moviedetails.MovieDetailsViewItem
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewNavigationHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewNavigationHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.handleErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val sessionProvider: SessionProvider
) :
    ViewModel(),
    ViewEventHolder by ViewEventHolderImpl(),
    ViewNavigationHolder by ViewNavigationHolderImpl(),
    ViewStateHolder<MoviesViewState> by ViewStateHolderImpl() {

    private val tag = javaClass.simpleName
    private var appLocale = LocaleUtils.getCurrentLocale()
    private var moviesPagerIndex = 1
    private var movies: List<MovieDTO> = arrayListOf()

    fun onEvent(event: MoviesViewEvent) {
        when (event) {
            MoviesViewEvent.OnViewInitialized -> onViewInitialised()
            MoviesViewEvent.OnBottomReached -> onBottomReached()
            is MoviesViewEvent.OnConfigurationChanged -> onConfigurationChanged(event.newLocale)
            is MoviesViewEvent.OnOpenDetailsClicked -> onOpenDetailsClicked(event.movie)
        }
    }

    private fun onViewInitialised() {
        updateState {
            MoviesViewState(showLoading = true)
        }
        loadMovies(moviesPagerIndex)
    }

    private fun loadMovies(moviesPagerIndex: Int) {
        viewModelScope.launch {
            getMoviesUseCase(moviesPagerIndex).handle(
                onError = { error ->
                    Log.d(tag, "movies error response: $error")
                    // hide the progressbar
                    updateState {
                        it!!.copy(showLoading = false)
                    }
                    // pass the error to the error handler
                    handleErrors(error) {
                        // if the error handler can not handle the error, we can do it here
                    }
                },
                onSuccess = { response ->
                    Log.d(tag, "movies success response: $response")
                    movies = movies + response
                    updateUiList(movies)
                }
            )
        }
    }

    private fun updateUiList(movies: List<MovieDTO>) {
        // map DTO to UI object
        val movieViewItems = movies.map {
            MovieViewItem(
                it.id,
                it.title,
                it.overview,
                sessionProvider.STATIC_IMAGE_URL + it.posterPath
            )
        }
        // update the UI state
        updateState {
            it!!.copy(showLoading = false, listItems = movieViewItems)
        }
    }

    private fun onConfigurationChanged(newLocale: Locale) {
        val isLocaleChanged = LocaleUtils.isLocaleChanged(appLocale, newLocale)
        Log.d(tag, "isLocaleChanged$isLocaleChanged")
        // reload the data with the new language
        if (isLocaleChanged) {
            onViewInitialised()
        }
        appLocale = newLocale
    }

    private fun onBottomReached() {
        Log.d(tag, "onBottomReached")
        moviesPagerIndex++
        loadMovies(moviesPagerIndex)
    }

    private fun onOpenDetailsClicked(movie: MovieViewItem) {
        Log.d(tag, "onOpenDetailsClicked $movie")
        viewModelScope.launch {
            getMovieDetailsUseCase(movie.id).handle(
                {
                    handleErrors(it)
                },
                { movieDTO ->
                    val genreNames = movieDTO.genres?.map { it.name }.orEmpty()
                    val trailerUrl = movieDTO.videos?.results?.find { it.official }?.ytKey
                        ?: movieDTO.videos?.results?.first()?.ytKey
                    val movieDetailsViewItem = MovieDetailsViewItem(
                        title = movieDTO.title,
                        description = movieDTO.overview,
                        posterImageUrl = sessionProvider.STATIC_IMAGE_URL + movieDTO.posterPath,
                        backdropImageUrl = sessionProvider.STATIC_IMAGE_URL + movieDTO.backdropPath,
                        releaseDate = movieDTO.releaseDate,
                        voteAvg = movieDTO.voteAverage,
                        voteNr = movieDTO.voteCount,
                        genre = genreNames,
                        ytTrailerKey = trailerUrl ?: ""
                    )
                    newNavigationEvent(OpenMovieDetails(movieDetailsViewItem))
                }
            )
        }
    }
}
