package com.hacker.news.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hacker.news.di.scope.HackerNewsAppScope
import javax.inject.Inject
import javax.inject.Provider

@HackerNewsAppScope
class HackerNewsViewModelFactory @Inject constructor(
    private val providerMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = providerMap[modelClass] ?: getProvider(modelClass)
        try {
            @Suppress("UNCHECKED_CAST")
            return provider.get() as T
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    private fun <T> getProvider(modelClass: Class<T>): Provider<ViewModel> {
        for ((key, value) in providerMap) {
            if (modelClass.isAssignableFrom(key)) {
                return value
            }
        }
        throw IllegalArgumentException(
            """
                $modelClass can't be initialized from
                ${HackerNewsViewModelFactory::class.java.simpleName}
            """.trimIndent()
        )
    }
}