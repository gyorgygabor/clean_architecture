package com.gabor.cleanarchitecture.presentation.utils.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding

/**
 * Wrapper class for view binding. Used to extract the view binding related codebase in the Fragments.
 */
interface ViewBindingHolder<T : ViewBinding> {
    /**
     * Saves the binding for cleanup on onDestroy, calls the specified function [onBound] with
     * `this` value as its receiver and returns the bound view root.
     */
    fun initBinding(binding: T, fragment: Fragment): View

    /**
     * Calls the specified [block] with the binding as `this` value and returns the binding. As a
     * consequence, this method can be used with a code block lambda in [block] or to initialize a
     * variable with the return type.
     *
     * @throws IllegalStateException if not currently holding a ViewBinding (when called outside
     * of an active fragment's lifecycle)
     */
    fun requireBinding(block: (T.() -> Unit)? = null): T
}

open class ViewBindingHolderImpl<T : ViewBinding> : ViewBindingHolder<T>, LifecycleObserver {

    private var binding: T? = null

    private lateinit var lifecycle: Lifecycle
    internal lateinit var fragmentName: String

    /**
     * To not leak memory we nullify the binding when the view is destroyed.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroyView() {
        lifecycle.removeObserver(this) // not mandatory, but preferred
        binding = null
    }

    override fun requireBinding(block: (T.() -> Unit)?) =
        binding?.apply { block?.invoke(this) }
            ?: throw IllegalStateException(
                "Accessing binding outside of Fragment lifecycle: $fragmentName"
            )


    override fun initBinding(binding: T, fragment: Fragment): View {
        this.binding = binding
        lifecycle = fragment.viewLifecycleOwner.lifecycle
        lifecycle.addObserver(this)
        fragmentName = fragment::class.simpleName ?: "N/A"
        return binding.root
    }
}