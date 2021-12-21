package com.gabor.assessment.presentation.utils.statehandler

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
        _viewState.value = stateCopy(oldState)// TODO find a solution for the initial state nullability without forcing the user to have non-null initial state
    }

    override val viewState: LiveData<T>
        get() = _viewState
}

interface ViewStateHolder<T> {

    fun updateState(stateCopy: (T?) -> T)
    val viewState: LiveData<T>
}

fun <T>Fragment.observeState(viewStateHolder: ViewStateHolder<T>, onUpdate: (T) -> Unit){
    viewStateHolder.viewState.observe(viewLifecycleOwner, {
        onUpdate(it)
    })
}