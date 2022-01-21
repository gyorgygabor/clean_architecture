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

fun Fragment.observeNavigationEvent(viewEventHolder: ViewNavigationHolder, onUpdate: (NavigationEvent) -> Unit){
    viewEventHolder.navigationEvents.observe(viewLifecycleOwner, {
        onUpdate(it)
    })
}

open class NavigationEvent