package com.hacker.news

import java.io.File

fun Any.getJsonFromFile(path: String): String {
    // Load the JSON response
    val uri = this.javaClass.classLoader?.getResource(path) ?: return ""
    val file = File(uri.path)
    return String(file.readBytes())
}