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