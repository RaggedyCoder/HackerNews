package com.hacker.news.util.customtabs

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder


class KeepAliveService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return sBinder
    }

    companion object {
        private val sBinder = Binder()
    }
}