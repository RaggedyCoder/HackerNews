package com.hacker.news.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hacker.news.util.*
import java.util.concurrent.ConcurrentHashMap

open class HackerNewsBaseViewModel(application: Application) : AndroidViewModel(application) {


    companion object {
        const val DEFAULT_PERMISSION_REQUEST_CODE = 2911
        private val LOCK = Any()
    }

    val alertLiveData: LiveData<AlertData> = MutableLiveData()

    val activityActionLiveData: LiveData<ActivityActionData> = MutableLiveData()

    val internetConnectionLiveData: LiveData<Boolean> = MutableLiveData()

    private val noInternetRetryActionMap = ConcurrentHashMap<Int, (() -> Unit)>()

    fun showToast(message: CharSequence) = showAlert(AlertType.Toast, message)

    fun showSnackbar(
        message: CharSequence,
        actionTitle: String? = null,
        action: (() -> Unit)? = null
    ) = if (actionTitle != null) {
        showAlert(AlertType.Snackbar, message, positiveAction = AlertActionData(actionTitle, action = action))
    } else {
        showAlert(AlertType.Snackbar, message)
    }

    fun showDialog(
        message: CharSequence,
        title: String? = null,
        positiveActionTitle: String? = null,
        positiveActionCallback: (() -> Unit)? = null,
        negativeActionTitle: String? = null,
        negativeActionCallback: (() -> Unit)? = null
    ) {
        val positiveActionData = if (positiveActionTitle != null) {
            AlertActionData(positiveActionTitle, positiveActionCallback)
        } else {
            null
        }

        val negativeActionData = if (negativeActionTitle != null) {
            AlertActionData(negativeActionTitle, negativeActionCallback)
        } else {
            null
        }
        showAlert(AlertType.Dialog, message, title, positiveActionData, negativeActionData)
    }

    private fun showAlert(
        alertType: AlertType,
        message: CharSequence,
        title: String? = null,
        positiveAction: AlertActionData? = null,
        negativeAction: AlertActionData? = null
    ) {
        (alertLiveData as MutableLiveData).value = alert {
            this.alertType = alertType
            this.message = message
            this.title = title
            this.positiveAction = positiveAction
            this.negativeAction = negativeAction
        }
    }

    fun startActivity(intent: Intent, shouldFinishCallingActivity: Boolean = false) =
        activityAction(ActivityActionType.SwitchActivity, intent, shouldFinishCallingActivity)

    fun startActivityForResult(requestCode: Int, intent: Intent, shouldFinishCallingActivity: Boolean = false) {
        activityAction(ActivityActionType.SwitchActivityForResult, intent, shouldFinishCallingActivity, requestCode)
    }

    fun finishActivity(resultCode: Int, intent: Intent) {
        activityAction(ActivityActionType.FinishActivity, intent, resultCode = resultCode)
    }

    fun activityAction(
        activityActionType: ActivityActionType,
        intent: Intent,
        shouldFinishCallingActivity: Boolean = false,
        requestCode: Int = DEFAULT_PERMISSION_REQUEST_CODE,
        resultCode: Int = Activity.RESULT_OK
    ) {
        (activityActionLiveData as MutableLiveData).value = activityAction {
            this.activityActionType = activityActionType
            this.intent = intent
            this.shouldFinishCallingActivity = shouldFinishCallingActivity
            this.requestCode = requestCode
            this.resultCode = resultCode
        }
    }

    fun setNoInternetRetry(noInternetRetryAction: () -> Unit) {
        noInternetRetryActionMap[noInternetRetryActionMap.size + 1] = noInternetRetryAction
        setInternetConnectionStatus(false)
    }

    fun performNoInternetFailedAction() {
        synchronized(LOCK) {
            if (getApplication<Application>().isOnline()) {
                for (key in noInternetRetryActionMap.keys()) {
                    noInternetRetryActionMap[key]?.invoke()
                }
                setInternetConnectionStatus(true)
                noInternetRetryActionMap.clear()
            }
        }
    }

    private fun setInternetConnectionStatus(hasInternetConnection: Boolean) {
        (internetConnectionLiveData as MutableLiveData).value = hasInternetConnection
    }

    protected fun getString(@StringRes stringResId: Int) = getApplication<Application>().getString(stringResId)
    protected fun getString(@StringRes stringResId: Int, vararg arguments: Any) =
        getApplication<Application>().getString(stringResId, *arguments)
}