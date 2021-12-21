package com.gabor.assessment.presentation.utils.statehandler

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

fun Fragment.observeViewEvent(viewEventHolder: ViewEventHolder, onUpdate: (ViewEvent) -> Unit){
    viewEventHolder.viewEvents.observe(viewLifecycleOwner, {
        onUpdate(it)
    })
}

open class ViewEvent{
    object ShowNoInternet : ViewEvent()
    object ShowSessionExpired : ViewEvent()
    object ShowServerUnreachable : ViewEvent()
}