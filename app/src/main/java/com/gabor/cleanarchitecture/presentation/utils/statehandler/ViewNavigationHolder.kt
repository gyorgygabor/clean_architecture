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
class ViewNavigationHolderImpl : ViewNavigationHolder {
    private val _navigationEvents = SingleLiveEvent<NavigationEvent>()

    override fun newNavigationEvent(event: NavigationEvent) {
        _navigationEvents.value = event
    }

    override val navigationEvents: LiveData<NavigationEvent>
        get() = _navigationEvents
}

interface ViewNavigationHolder {

    fun newNavigationEvent(event: NavigationEvent)
    val navigationEvents: LiveData<NavigationEvent>
}

fun Fragment.observeNavigationEvent(viewEventHolder: ViewNavigationHolder, onUpdate: (NavigationEvent) -> Unit) {
    viewEventHolder.navigationEvents.observe(
        viewLifecycleOwner,
        {
            onUpdate(it)
        }
    )
}

open class NavigationEvent
