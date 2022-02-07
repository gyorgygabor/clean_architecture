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
package com.gabor.cleanarchitecture.presentation.utils.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
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

open class ViewBindingHolderImpl<T : ViewBinding> : ViewBindingHolder<T>, DefaultLifecycleObserver {

    private var binding: T? = null

    private lateinit var lifecycle: Lifecycle
    internal lateinit var fragmentName: String

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
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
