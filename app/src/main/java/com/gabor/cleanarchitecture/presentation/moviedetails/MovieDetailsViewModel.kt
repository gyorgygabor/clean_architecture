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
    ViewStateHolder<MovieDetailsViewState> by ViewStateHolderImpl() {

    private val tag = javaClass.simpleName

    fun onViewInitialised(movieViewItem: MovieDetailsViewItem?) {
        // update the UI state
        updateState {
            // map params to view state
            val releaseYear = movieViewItem?.releaseDate?.take(4).orEmpty()
            val updatedMovieDetails = movieViewItem!!.copy(releaseDate = releaseYear)
            // send the new state
            it!!.copy(movieDetails = updatedMovieDetails)
        }
    }
}
