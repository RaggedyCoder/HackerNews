package com.hacker.news.model

import io.reactivex.Scheduler

data class RxSchedulers(
    val io: Scheduler,
    val computation: Scheduler,
    val mainThread: Scheduler
)