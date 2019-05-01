package com.hacker.news.api.response

import java.util.*

data class UserInfoResponse(
    val id: String,
    val created: Date,
    val karma: Int,
    val delay: Int?,
    val about: String?,
    val submitted: List<Int>?
)