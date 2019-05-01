package com.hacker.news.util

import android.util.Log
import com.hacker.news.BuildConfig
import com.hacker.news.di.scope.HackerNewsAppScope
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException
import javax.inject.Inject

@HackerNewsAppScope
@Suppress("unused", "MemberVisibilityCanBePrivate")
class HackerNewsLogger @Inject constructor() {

    companion object {
        private const val MAX_LOG_LENGTH = 1028
    }

    fun v(`class`: Class<*>, msg: String) = v(`class`.canonicalName ?: `class`.simpleName, msg)

    fun v(tag: String, msg: String) =
        println(Log.VERBOSE, tag, msg)

    fun v(`class`: Class<*>, msg: String, tr: Throwable) =
        v(`class`.canonicalName ?: `class`.simpleName, msg, tr)

    fun v(tag: String, msg: String, tr: Throwable) =
        println(Log.VERBOSE, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun d(`class`: Class<*>, msg: String) =
        d(`class`.canonicalName ?: `class`.simpleName, msg)

    fun d(tag: String, msg: String) = println(Log.DEBUG, tag, msg)

    fun d(`class`: Class<*>, msg: String, tr: Throwable) =
        d(`class`.canonicalName ?: `class`.simpleName, msg, tr)

    fun d(tag: String, msg: String, tr: Throwable) =
        println(Log.DEBUG, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun i(`class`: Class<*>, msg: String) =
        i(`class`.canonicalName ?: `class`.simpleName, msg)

    fun i(tag: String, msg: String) =
        println(Log.INFO, tag, msg)

    fun i(`class`: Class<*>, msg: String, tr: Throwable) =
        i(`class`.canonicalName ?: `class`.simpleName, msg, tr)

    fun i(tag: String, msg: String, tr: Throwable) =
        println(Log.INFO, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun w(tag: String, msg: String) = println(Log.WARN, tag, msg)

    fun w(tag: String, msg: String, tr: Throwable) =
        println(Log.WARN, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun w(tag: String, tr: Throwable) = println(Log.WARN, tag, getStackTraceString(tr))

    fun wtf(tag: String, msg: String) = println(Log.WARN, tag, msg)

    fun wtf(tag: String, msg: String, tr: Throwable) =
        println(Log.WARN, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun wtf(tag: String, tr: Throwable) = println(Log.WARN, tag, getStackTraceString(tr))

    fun e(tag: String, msg: String) = println(Log.ERROR, tag, msg)

    fun e(tag: String, msg: String, tr: Throwable) =
        println(Log.ERROR, tag, "$msg+\n+${getStackTraceString(tr)}")

    fun e(`class`: Class<*>, msg: String) = e(`class`.canonicalName ?: `class`.simpleName, msg)

    fun e(`class`: Class<*>, msg: String, tr: Throwable) =
        e(`class`.canonicalName ?: `class`.simpleName, "$msg+\n+${getStackTraceString(tr)}")

    private fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    private fun println(priority: Int, tag: String, message: String) =
        if (BuildConfig.DEBUG) {
            var i = 0
            val length = message.length
            var result = -1
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(newline, i + MAX_LOG_LENGTH)
                    result = Log.println(priority, smartTag(tag), message.substring(i, end))
                    i = end
                } while (i < newline)
                i++
            }
            result
        } else {
            -1
        }

    private fun smartTag(tag: String): String {
        val splitterString = tag.split("\\.".toRegex())
        val tagBuilder = StringBuilder()
        for (i in 0 until splitterString.size) {
            if (i != splitterString.size - 1) {
                tagBuilder.append(splitterString[i][0]).append(".")
            } else {
                tagBuilder.append(splitterString[i])
            }
        }
        return tagBuilder.toString()
    }
}