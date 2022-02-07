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
package com.gabor.cleanarchitecture.presentation.utils.statehandler

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Flow wrapper which is used for view states.
 */
class ViewStateHolderImpl<T> : ViewStateHolder<T> {

    private val _viewState = MutableStateFlow<T?>(null)

    override fun updateState(stateCopy: (T?) -> T) {
        val oldState = _viewState.value
        _viewState.value = stateCopy(oldState)
    }

    override val viewState: StateFlow<T?>
        get() = _viewState.asStateFlow()
}

interface ViewStateHolder<T> {

    fun updateState(stateCopy: (T?) -> T)
    val viewState: StateFlow<T?>
}

fun <T> Fragment.observeState(viewStateHolder: ViewStateHolder<T>, onUpdate: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        viewStateHolder.viewState.collectLatest { state ->
            state?.let {
                onUpdate(it)
            }
        }
    }
}
