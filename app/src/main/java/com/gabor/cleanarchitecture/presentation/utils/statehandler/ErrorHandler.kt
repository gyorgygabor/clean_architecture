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
import com.gabor.cleanarchitecture.presentation.utils.dialogs.DialogFactory
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * Contains the handling for viewEvents with network related issues.
 */
fun Fragment.observeNetworkErrorEvents(viewModel: ViewEventHolder) {
    viewModel.viewEvents.observe(
        viewLifecycleOwner
    ) {
        handleNetworkErrorEvent(it)
    }
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
