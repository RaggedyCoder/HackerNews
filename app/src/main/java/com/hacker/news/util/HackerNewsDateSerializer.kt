package com.hacker.news.util

import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.hacker.news.di.scope.HackerNewsAppScope
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

@HackerNewsAppScope
class HackerNewsDateSerializer @Inject constructor() : JsonSerializer<Date> {
    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?) =
        if (src == null) {
            null
        } else {
            JsonPrimitive(src.time / 1000)
        }

}
