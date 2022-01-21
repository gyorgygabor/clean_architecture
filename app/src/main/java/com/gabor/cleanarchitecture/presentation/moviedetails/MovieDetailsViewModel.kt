package com.gabor.cleanarchitecture.presentation.moviedetails

import androidx.lifecycle.ViewModel
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolderImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor() :
    ViewModel(),
    ViewEventHolder by ViewEventHolderImpl(),
    ViewStateHolder<MovieDetailsViewItem> by ViewStateHolderImpl() {

    private val tag = javaClass.simpleName


    fun onViewInitialised(movieViewItem: MovieDetailsViewItem?) {
    // update the UI state
        updateState {
            val releaseYear = movieViewItem?.releaseDate?.take(4).orEmpty()
            movieViewItem!!.copy(releaseDate = releaseYear)
        }
    }
}

