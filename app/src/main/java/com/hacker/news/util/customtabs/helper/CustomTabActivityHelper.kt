package com.hacker.news.util.customtabs.helper

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import com.hacker.news.di.scope.HackerNewsAppScope
import com.hacker.news.util.customtabs.CustomTabsServiceConnectionImpl
import com.hacker.news.util.customtabs.ServiceConnectionCallback
import com.hacker.news.util.customtabs.getAvailableChromePackage
import javax.inject.Inject

@HackerNewsAppScope
class CustomTabActivityHelper @Inject constructor() : ServiceConnectionCallback {

    private var customTabsSession: CustomTabsSession? = null
    private var client: CustomTabsClient? = null
    private var connection: CustomTabsServiceConnection? = null
    private var connectionCallback: ConnectionCallback? = null

    override fun onServiceConnected(client: CustomTabsClient) {
        this.client = client
        client.warmup(0L)
        connectionCallback?.onCustomTabsConnected()
    }

    override fun onServiceDisconnected() {
        this.client = null
        customTabsSession = null
        connectionCallback?.onCustomTabsDisconnected()
    }

    fun getSession(): CustomTabsSession? {
        if (client == null) {
            customTabsSession = null
        } else if (customTabsSession == null) {
            customTabsSession = client?.newSession(null)
        }
        return customTabsSession
    }

    fun setConnectionCallback(connectionCallback: ConnectionCallback) {
        this.connectionCallback = connectionCallback
    }

    /**
     * Binds the Activity to the Custom Tabs Service.
     * @param context the context to be binded to the service.
     */
    fun bindCustomTabsService(context: Context) {
        try {
            if (client != null) return

            val packageName = context.getAvailableChromePackage()

            connection = CustomTabsServiceConnectionImpl(this)
            CustomTabsClient.bindCustomTabsService(context, packageName, connection)
        } catch (ex: Exception) {
        }
    }

    /**
     * Unbinds the Activity from the Custom Tabs Service.
     * @param context the Context that is connected to the service.
     */
    fun unbindCustomTabsService(context: Context) {
        try {
            if (connection == null) return
            context.unbindService(connection)
            client = null
            customTabsSession = null
            connection = null
        } catch (ex: Exception) {
        }
    }

    /**
     * @see {@link CustomTabsSession.mayLaunchUrl
     * @return true if call to mayLaunchUrl was accepted.
     */
    fun mayLaunchUrl(uri: Uri, extras: Bundle, otherLikelyBundles: List<Bundle>): Boolean {
        client ?: return false
        return getSession()?.mayLaunchUrl(uri, extras, otherLikelyBundles) ?: false
    }

    /**
     * A Callback for when the service is connected or disconnected. Use those callbacks to
     * handle UI changes when the service is connected or disconnected.
     */
    interface ConnectionCallback {
        /**
         * Called when the service is connected.
         */
        fun onCustomTabsConnected()

        /**
         * Called when the service is disconnected.
         */
        fun onCustomTabsDisconnected()
    }
}