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

/**
 * LiveData wrapper which is used for view events like: dialogs, snack-bars.
 */
class ViewEventHolderImpl : ViewEventHolder {
    private val _viewEvents = SingleLiveEvent<ViewEvent>()

    override fun newViewEvent(event: ViewEvent) {
        _viewEvents.value = event
    }

    override val viewEvents: LiveData<ViewEvent>
        get() = _viewEvents
}

interface ViewEventHolder {

    fun newViewEvent(event: ViewEvent)
    val viewEvents: LiveData<ViewEvent>
}

fun Fragment.observeViewEvent(viewEventHolder: ViewEventHolder, onUpdate: (ViewEvent) -> Unit) {
    viewEventHolder.viewEvents.observe(
        viewLifecycleOwner,
        {
            onUpdate(it)
        }
    )
}

open class ViewEvent {
    object ShowNoInternet : ViewEvent()
    object ShowSessionExpired : ViewEvent()
    object ShowServerUnreachable : ViewEvent()
}
