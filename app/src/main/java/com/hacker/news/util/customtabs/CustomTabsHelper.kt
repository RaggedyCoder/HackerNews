package com.hacker.news.util.customtabs

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.hacker.news.BuildConfig

const val STABLE_PACKAGE = "com.android.chrome"
const val BETA_PACKAGE = "com.chrome.beta"
const val DEV_PACKAGE = "com.chrome.dev"
const val LOCAL_PACKAGE = "com.google.android.apps.chrome"

private const val EXTRA_CUSTOM_TABS_KEEP_ALIVE = "android.support.customtabs.extra.KEEP_ALIVE"
private const val ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService"

/**
 * Opens the URL on a Custom Tab if possible. Otherwise fallsback to opening it on a WebView.
 *
 * @param customTabsIntent a CustomTabsIntent to be used if Custom Tabs is available.
 * @param uri the Uri to be opened.
 * @param fallback a CustomTabFallback to be used if Custom Tabs is not available.
 */
fun Context.openCustomTab(
    customTabsIntent: CustomTabsIntent, uri: Uri, fallback: ((context: Context, uri: Uri) -> Unit)? = null
) {
    try {
        val packageName = getAvailableChromePackage()

        //If we cant find a package name, it means there's no browser that supports
        //Chrome Custom Tabs installed. So, we fallback to the WebView
        if (packageName.isBlank()) {
            fallback?.invoke(this, uri)
        } else {
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(this, uri)
        }
    } catch (ex: Exception) {
        fallback?.invoke(this, uri)
    }
}

/**
 * Opens the URL on a Custom Tab if possible. Otherwise fallsback to opening it on a WebView.
 *
 * @param customTabsIntent a CustomTabsIntent to be used if Custom Tabs is available.
 * @param uri the Uri to be opened.
 * @param fallback a CustomTabFallback to be used if Custom Tabs is not available.
 */
fun Fragment.openCustomTab(
    customTabsIntent: CustomTabsIntent, uri: Uri, fallback: ((context: Context, uri: Uri) -> Unit)? = null
) = context?.openCustomTab(customTabsIntent, uri, fallback)

fun Context.addKeepAliveExtra(intent: Intent) {
    val className = KeepAliveService::class.java.canonicalName ?: return
    val keepAliveIntent = Intent().setClassName(packageName, className)
    intent.putExtra(EXTRA_CUSTOM_TABS_KEEP_ALIVE, keepAliveIntent)
}

fun Context.getAvailableChromePackage(): String {
    val actionViewIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
    val defaultViewHandlerPackageName =
        packageManager.resolveActivity(actionViewIntent, 0)?.activityInfo?.packageName ?: ""
    val resolvedActivityList = packageManager.queryIntentActivities(actionViewIntent, 0)

    val packagesSupportingCustomTabs = ArrayList<String>()
    for (resolvedActivity in resolvedActivityList) {
        val serviceIntent = Intent()
        serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
        serviceIntent.setPackage(resolvedActivity.activityInfo.packageName)
        if (packageManager.resolveService(serviceIntent, 0) != null) {
            packagesSupportingCustomTabs += resolvedActivity.activityInfo.packageName
        }
    }

    return when {
        packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs[0]
        defaultViewHandlerPackageName.isNotBlank() && !hasSpecializedHandlerIntents(actionViewIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName) -> defaultViewHandlerPackageName
        packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
        packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
        packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
        packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
        else -> ""
    }
}

private fun Context.hasSpecializedHandlerIntents(intent: Intent): Boolean {
    try {
        val pm = packageManager
        val handlers = pm.queryIntentActivities(
            intent,
            PackageManager.GET_RESOLVED_FILTER
        )
        if (handlers == null || handlers.size == 0) {
            return false
        }
        for (resolveInfo in handlers) {
            val filter = resolveInfo.filter ?: continue
            if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
            if (resolveInfo.activityInfo == null) continue
            return true
        }
    } catch (e: RuntimeException) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }
    return false
}