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

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.moviedetails.MovieDetailsViewItem
import com.gabor.cleanarchitecture.presentation.theme.AppTheme
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import com.gabor.cleanarchitecture.presentation.utils.LogCompositions
import com.gabor.cleanarchitecture.presentation.utils.MOVIE_DETAIL_PARAM
import com.gabor.cleanarchitecture.presentation.utils.statehandler.NavigationEvent
import com.gabor.cleanarchitecture.presentation.utils.statehandler.handleNetworkErrorEvent
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeNavigationEvent
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeState
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeViewEvent
import com.gabor.cleanarchitecture.presentation.utils.views.toggleProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * Movies list screen.
 */
const val TAG = "MoviesFragment"

@InternalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel by viewModels<MoviesViewModel>()

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme() {
                    val viewState by viewModel.viewState.observeAsState()
                    LogCompositions(TAG, "ComposeView called ${viewState?.listItems?.size}")
                    MoviesListView(
                        viewState?.listItems.orEmpty(),
                        { viewModel.onEvent(MoviesViewEvent.OnBottomReached) },
                        { viewModel.onEvent(MoviesViewEvent.OnOpenDetailsClicked(it)) }
                    )
                    EmptyListView(viewState?.listItems.isNullOrEmpty())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewModel.onEvent(MoviesViewEvent.OnViewInitialized)
    }

    private fun setupListeners() {
        // observe the view states
        observeState(viewModel) {
            toggleProgressBar(it.showLoading)
        }

        // observe the view events
        observeViewEvent(viewModel) {
            // shows the corresponding error dialogs
            handleNetworkErrorEvent(it)
        }

        observeNavigationEvent(viewModel) {
            when (it) {
                is OpenMovieDetails -> {
                    val bundle = bundleOf(MOVIE_DETAIL_PARAM to it.movieDetailsViewItem)
                    findNavController().navigate(R.id.movieDetailsFragment, bundle)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.onEvent(MoviesViewEvent.OnConfigurationChanged(newConfig.locales.get(0) ?: LocaleUtils.getCurrentLocale()))
    }
}

// View state
data class MoviesViewState(
    val showLoading: Boolean = false,
    val listItems: List<MovieViewItem> = arrayListOf()
)

data class MovieViewItem(
    val id: Int,
    val title: String,
    val description: String,
    val posterImageUrl: String
)

// View navigation
data class OpenMovieDetails(val movieDetailsViewItem: MovieDetailsViewItem) : NavigationEvent()
