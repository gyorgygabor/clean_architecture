package com.gabor.assessment.presentation.utils.statehandler

import androidx.fragment.app.Fragment
import com.gabor.assessment.presentation.utils.dialogs.DialogFactory
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


/**
 * Contains the handling for viewEvents with network related issues.
 */
fun Fragment.observeNetworkErrorEvents(viewModel: ViewEventHolder) {
    viewModel.viewEvents.observe(viewLifecycleOwner, {
        handleNetworkErrorEvent(it)
    })
}

fun Fragment.handleNetworkErrorEvent(it: ViewEvent?) {
    when (it) {
        ViewEvent.ShowSessionExpired -> {
            DialogFactory.showSessionExpiredDialog(
                context = requireContext()
            ) {
                // clear the back stack and open the login screen
            }
        }
        ViewEvent.ShowServerUnreachable -> {
            DialogFactory.showServerErrorDialog(
                context = requireContext()
            )
        }
        ViewEvent.ShowNoInternet -> {
            DialogFactory.showNetworkErrorDialog(
                context = requireContext()
            )
        }
    }
}


fun ViewEventHolder.handleErrors(throwable: Throwable?, fallback: (() -> Unit)? = null) {
    when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    newViewEvent(ViewEvent.ShowSessionExpired)
                }
                in 500..505 -> {
                    newViewEvent(ViewEvent.ShowServerUnreachable)
                }
                else -> fallback?.invoke()
            }
        }
        is SocketTimeoutException,
        is UnknownHostException -> {
            newViewEvent(ViewEvent.ShowNoInternet)
        }
        is SSLHandshakeException -> {
            newViewEvent(ViewEvent.ShowServerUnreachable)
        }
        is java.net.UnknownServiceException -> {
            newViewEvent(ViewEvent.ShowServerUnreachable)
        }
        else -> fallback?.invoke()
    }
}
