package com.hacker.news.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat


fun Context.isOnline(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo ?: return false
    return netInfo.isConnected
}

fun Context.getCompatColorStateList(colorStateListId: Int) =
    ResourcesCompat.getColorStateList(this.resources, colorStateListId, this.theme)!!

fun Context.getCompatColor(colorId: Int) =
    ResourcesCompat.getColor(this.resources, colorId, this.theme)

fun Drawable.setCompatTintList(colorStateList: ColorStateList) {
    DrawableCompat.setTintList(this, colorStateList)
}

fun Drawable.setCompatTint(tintId: Int) {
    DrawableCompat.setTint(this, tintId)
}