package com.gabor.cleanarchitecture.presentation.utils.views

import android.view.View
import androidx.compose.foundation.lazy.LazyListState
import androidx.fragment.app.Fragment
import com.gabor.cleanarchitecture.presentation.MainActivity

/**
 * Shows/hide the progress bar.
 * @param shouldShow - Whether the progress bar should be shown or not.
 */
fun Fragment.toggleProgressBar(shouldShow: Boolean){
    (requireActivity() as? MainActivity)?.binding?.progressBar?.visibility = if (shouldShow) View.VISIBLE else View.GONE
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
