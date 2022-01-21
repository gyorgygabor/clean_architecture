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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabor.cleanarchitecture.databinding.FragmentMoviesBinding
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import com.gabor.cleanarchitecture.presentation.utils.statehandler.handleNetworkErrorEvent
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeState
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeViewEvent
import com.gabor.cleanarchitecture.presentation.utils.toggleProgressBar
import com.gabor.cleanarchitecture.presentation.utils.viewbinding.ViewBindingHolder
import com.gabor.cleanarchitecture.presentation.utils.viewbinding.ViewBindingHolderImpl
import dagger.hilt.android.AndroidEntryPoint

/**
 * Movies list screen.
 */

@AndroidEntryPoint
class MoviesFragment :
    Fragment(),
    ViewBindingHolder<FragmentMoviesBinding> by ViewBindingHolderImpl() {

    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(FragmentMoviesBinding.inflate(inflater, container, false), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.onViewInitialised()
    }

    private fun setupViews() {
        requireBinding {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = MoviesAdapter(arrayListOf())
        }

        // observe the view states
        observeState(viewModel) {
            toggleProgressBar(it.showLoading)
            toggleEmptyListView(it.listItems.isEmpty())
            requireBinding().recyclerView.adapter = MoviesAdapter(it.listItems)
        }

        // observe the view events
        observeViewEvent(viewModel) {
            // shows the corresponding error dialogs
            handleNetworkErrorEvent(it)
        }

        // For navigation we can create a navEvent using the SingleLiveEvent and observe the navigation events here.
    }

    private fun toggleEmptyListView(isEmpty: Boolean) {
        requireBinding().emptyListView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.onConfigurationChanged(newConfig.locales.get(0) ?: LocaleUtils.getCurrentLocale())
    }
}

// View state
data class MoviesViewState(
    val showLoading: Boolean = false,
    val listItems: List<MovieViewItem> = arrayListOf()
)

data class MovieViewItem(val title: String, val description: String, val imageUrl: String)
