package com.hacker.news.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.hacker.news.di.scope.HackerNewsAppScope
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

@HackerNewsAppScope
class HackerNewsDateDeserializer @Inject constructor() : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?) =
        if (json == null) {
            null
        } else {
            Date(json.asJsonPrimitive.asLong * 1000)
        }
}
