package com.hacker.news.util

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

sealed class AlertData(open val message: CharSequence)

data class ToastAlert(
    override val message: CharSequence,
    val duration: Int
) : AlertData(message)

data class SnackbarAlert(
    override val message: CharSequence,
    val duration: Int,
    val action: AlertActionData?
) : AlertData(message)

data class DialogAlert(
    override val message: CharSequence,
    val title: String?,
    val positiveAction: AlertActionData?,
    val negativeAction: AlertActionData?
) : AlertData(message)

data class InputErrorData(val viewId: Int, val errorMessage: String)

data class ProgressAlertData(val message: String, val title: String?, val isCancelable: Boolean)

enum class AlertType {
    Toast, Dialog, Snackbar
}

typealias AlertActionCallback = (() -> Unit)?

data class AlertActionData(
    val title: String,
    val action: AlertActionCallback
)

class InputErrorDataBuilder {
    var viewId: Int = -1
    var errorMessage: String = ""

    fun build() = InputErrorData(viewId, errorMessage)
}

class ProgressAlertDataBuilder {
    var message: String = ""
    var title: String? = null
    var isCancelable: Boolean = false

    fun build() = ProgressAlertData(message, title, isCancelable)
}


class AlertDataBuilder {
    var alertType = AlertType.Dialog
    var message:CharSequence = ""
    var toastDuration = Toast.LENGTH_SHORT
    var snackbarDuration = Snackbar.LENGTH_SHORT
    var title: String? = null
    var positiveAction: AlertActionData? = null
    var negativeAction: AlertActionData? = null

    fun build() = when (alertType) {
        AlertType.Toast -> ToastAlert(message, toastDuration)
        AlertType.Dialog -> DialogAlert(message, title, positiveAction, negativeAction)
        AlertType.Snackbar -> SnackbarAlert(message, snackbarDuration, positiveAction ?: negativeAction)
    }
}

inline fun progressAlert(block: ProgressAlertDataBuilder.() -> Unit) = ProgressAlertDataBuilder().apply(block).build()

inline fun alert(block: AlertDataBuilder.() -> Unit) = AlertDataBuilder().apply(block).build()

inline fun inputError(block: InputErrorDataBuilder.() -> Unit) = InputErrorDataBuilder().apply(block).build()