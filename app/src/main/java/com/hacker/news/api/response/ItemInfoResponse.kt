package com.hacker.news.api.response

import java.io.Serializable
import java.util.*

data class ItemInfoResponse(
    val id: Int,
    val deleted: Boolean?,
    val type: String?,
    val by: String?,
    val time: Date?,
    val text: String?,
    val dead: Boolean?,
    val parent: Int?,
    val poll: Int?,
    val kids: List<Int>?,
    val url: String?,
    val score: Int?,
    val title: String?,
    val parts: List<Int>?,
    val descendants: Int?
) : Serializable