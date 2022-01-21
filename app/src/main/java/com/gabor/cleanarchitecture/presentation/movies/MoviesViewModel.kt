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
import com.gabor.cleanarchitecture.presentation.utils.statehandler.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
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


    fun onViewInitialised() {
        updateState {
            MoviesViewState(showLoading = true)
        }
        loadMovies(moviesPagerIndex)
    }

    private fun loadMovies(moviesPagerIndex: Int) {
        viewModelScope.launch {
            getMoviesUseCase(moviesPagerIndex).handle(onError = { error ->
                Log.d(tag, "movies error response: $error")
                // hide the progressbar
                updateState {
                    it!!.copy(showLoading = false)
                }
                // pass the error to the error handler
                handleErrors(error) {
                    // if the error handler can not handle the error, we can do it here
                }
            }, onSuccess = { response ->
                Log.d(tag, "movies success response: $response")
                movies = movies + response
                updateUiList(movies)
            })
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

    fun onConfigurationChanged(newLocale: Locale) {
        val isLocaleChanged = LocaleUtils.isLocaleChanged(appLocale, newLocale)
        Log.d(tag, "isLocaleChanged$isLocaleChanged")
        // reload the data with the new language
        if (isLocaleChanged) {
            onViewInitialised()
        }
        appLocale = newLocale
    }

    fun onBottomReached() {
        Log.d(tag, "onBottomReached")
        moviesPagerIndex++
        loadMovies(moviesPagerIndex)
    }

    fun onOpenDetailsClicked(movie: MovieViewItem) {
        Log.d(tag, "onOpenDetailsClicked $movie")
        viewModelScope.launch {
            getMovieDetailsUseCase(movie.id).handle({
                handleErrors(it)
            }, { movieDTO ->
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
            })
        }

    }
}

