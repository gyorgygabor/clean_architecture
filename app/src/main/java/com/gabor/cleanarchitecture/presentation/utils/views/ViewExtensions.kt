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
package com.gabor.cleanarchitecture.presentation.utils.views

import android.view.View
import androidx.compose.foundation.lazy.LazyListState
import androidx.fragment.app.Fragment
import com.gabor.cleanarchitecture.presentation.MainActivity

/**
 * Shows/hide the progress bar.
 * @param shouldShow - Whether the progress bar should be shown or not.
 */
fun Fragment.toggleProgressBar(shouldShow: Boolean) {
    (requireActivity() as? MainActivity)?.binding?.progressBar?.visibility = if (shouldShow) View.VISIBLE else View.GONE
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
