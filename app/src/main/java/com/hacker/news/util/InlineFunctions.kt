@file:Suppress("NOTHING_TO_INLINE")

package com.hacker.news.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

inline fun Context.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, resId, duration).show()

inline fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).apply {
        this.show()
    }

inline fun Fragment.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(resId, duration)

inline fun Fragment.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(message, duration)

inline fun Activity.showSnackbar(
    resId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionData: AlertActionData? = null
) = showSnackbar(getString(resId), duration, actionData)

inline fun Activity.showSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionData: AlertActionData? = null
) = Snackbar.make(findViewById<View>(android.R.id.content), message, duration).apply {
    if (actionData != null) {
        this.setAction(actionData.title) {
            actionData.action?.invoke()
            this.dismiss()
        }
    }
    this.show()
}

inline fun Fragment.showSnackbar(
    resId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionData: AlertActionData? = null
) = activity?.showSnackbar(resId, duration, actionData)

inline fun Fragment.showSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionData: AlertActionData? = null
) = activity?.showSnackbar(message, duration, actionData)

inline fun Activity.dialog(block: AlertDialog.Builder.() -> Unit) =
    AlertDialog.Builder(this).apply(block).create()

inline fun Fragment.dialog(block: AlertDialog.Builder.() -> Unit) =
    activity?.dialog(block)