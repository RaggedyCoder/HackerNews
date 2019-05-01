package com.hacker.news.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hacker.news.datasource.NetworkDataSource


abstract class NetworkDataSourceFactory<Key, Value> : DataSource.Factory<Key, Value>() {
    val sourceLiveData = MutableLiveData<NetworkDataSource<Key, Value>>()
}