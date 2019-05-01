package com.hacker.news.util

import java.io.Serializable

class Story private constructor(val storyPathParam: String) : Serializable {
    companion object {
        val TOP = Story("topstories")
        val NEW = Story("newstories")
        val BEST = Story("beststories")
        val ASK = Story("askstories")
        val SHOW = Story("showstories")
        val JOB = Story("jobstories")
    }
}
