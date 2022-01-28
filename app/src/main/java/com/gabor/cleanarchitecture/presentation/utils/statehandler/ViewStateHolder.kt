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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * LiveData wrapper which is used for view states.
 */
class ViewStateHolderImpl<T> : ViewStateHolder<T> {
    private val _viewState = MutableLiveData<T>()

    override fun updateState(stateCopy: (T?) -> T) {
        val oldState = _viewState.value
        _viewState.value = stateCopy(oldState) // TODO find a solution for the initial state nullability without forcing the user to have non-null initial state
    }

    override val viewState: LiveData<T>
        get() = _viewState
}

interface ViewStateHolder<T> {

    fun updateState(stateCopy: (T?) -> T)
    val viewState: LiveData<T>
}

fun <T> Fragment.observeState(viewStateHolder: ViewStateHolder<T>, onUpdate: (T) -> Unit) {
    viewStateHolder.viewState.observe(
        viewLifecycleOwner,
        {
            onUpdate(it)
        }
    )
}
