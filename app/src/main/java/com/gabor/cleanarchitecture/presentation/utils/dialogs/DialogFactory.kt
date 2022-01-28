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
package com.gabor.cleanarchitecture.presentation.utils.dialogs

import android.app.AlertDialog
import android.content.Context
import com.gabor.cleanarchitecture.R

/**
 * Responsible for creating the dialogs.
 */
object DialogFactory {
    fun showSessionExpiredDialog(context: Context, function: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.title_alert))
            setMessage(context.getString(R.string.label_session_expired))
            setNeutralButton(
                context.getString(R.string.label_login)
            ) { dialog, _ ->
                dialog.cancel()
                function.invoke()
            }
            create()
        }.show()
    }

    fun showServerErrorDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.title_alert))
            setMessage(context.getString(R.string.label_server_unreachable))
            setNeutralButton(
                context.getString(R.string.label_ok)
            ) { dialog, _ ->
                dialog.cancel()
            }
            create()
        }.show()
    }

    fun showNetworkErrorDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.title_alert))
            setMessage(context.getString(R.string.label_no_internet_connection))
            setNeutralButton(
                context.getString(R.string.label_ok)
            ) { dialog, _ ->
                dialog.cancel()
            }
            create()
        }.show()
    }
}
