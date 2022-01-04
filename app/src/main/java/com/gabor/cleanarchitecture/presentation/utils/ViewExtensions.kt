package com.gabor.cleanarchitecture.presentation.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.gabor.cleanarchitecture.presentation.MainActivity

/**
 * Shows/hide the progress bar.
 * @param shouldShow - Whether the progress bar should be shown or not.
 */
fun Fragment.toggleProgressBar(shouldShow: Boolean){
    (requireActivity() as? MainActivity)?.binding?.progressBar?.visibility = if (shouldShow) View.VISIBLE else View.GONE
}