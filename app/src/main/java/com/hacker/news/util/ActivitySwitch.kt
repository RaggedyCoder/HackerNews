package com.hacker.news.util

import android.content.Intent

sealed class ActivityActionData(
    open val intent: Intent?
)

data class FinishActivityActionData(
    val resultCode: Int,
    override val intent: Intent?
) : ActivityActionData(intent)

data class SwitchActivityActionData(
    override val intent: Intent,
    val shouldFinishCallingActivity: Boolean
) : ActivityActionData(intent)

data class SwitchActivityForResultActionData(
    val requestCode: Int,
    override val intent: Intent,
    val shouldFinishCallingActivity: Boolean
) : ActivityActionData(intent)


enum class ActivityActionType {
    SwitchActivity, SwitchActivityForResult, FinishActivity
}

class ActivityActionDataBuilder {
    var activityActionType = ActivityActionType.SwitchActivity
    var requestCode: Int = 0
    var resultCode: Int = 0
    var intent = Intent()
    var shouldFinishCallingActivity = false

    fun build(): ActivityActionData = when (activityActionType) {
        ActivityActionType.SwitchActivity -> SwitchActivityActionData(intent, shouldFinishCallingActivity)
        ActivityActionType.SwitchActivityForResult -> SwitchActivityForResultActionData(requestCode, intent, shouldFinishCallingActivity)
        ActivityActionType.FinishActivity -> FinishActivityActionData(resultCode, intent)
    }
}

inline fun activityAction(block: ActivityActionDataBuilder.() -> Unit) =
    ActivityActionDataBuilder().apply(block).build()