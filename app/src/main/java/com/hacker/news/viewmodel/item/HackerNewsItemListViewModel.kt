package com.hacker.news.viewmodel.item

import android.app.Application
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.*
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.datasource.fetcher.HackerNewsItemDataSourceFetcher
import com.hacker.news.model.*
import com.hacker.news.repository.HackerNewsStoryRepository
import com.hacker.news.util.Story
import com.hacker.news.util.customtabs.helper.CustomTabActivityHelper
import com.hacker.news.util.customtabs.openCustomTab
import com.hacker.news.util.getCompatColor
import com.hacker.news.util.setCompatTint
import com.hacker.news.viewmodel.HackerNewsBaseViewModel
import javax.inject.Inject

class HackerNewsItemListViewModel @Inject constructor(
    private val hackerNewsStoryRepository: HackerNewsStoryRepository,
    private val hackerNewsItemDataSourceFetcher: HackerNewsItemDataSourceFetcher,
    private val customTabActivityHelper: CustomTabActivityHelper,
    application: Application
) : HackerNewsBaseViewModel(application), LifecycleObserver {

    private var storyIds: List<Int>? = null
    private var story: Story? = null

    private val repositoryResult = MutableLiveData<Listing<ItemInfoResponse>>()
    val pagedData = Transformations.switchMap(repositoryResult) { it.pagedList }
    val networkState = Transformations.switchMap(repositoryResult) { it.networkState }
    val refreshState = Transformations.switchMap(repositoryResult) { it.refreshState }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        try {
            customTabActivityHelper.bindCustomTabsService(getApplication())
        } catch (ex: Exception) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        try {
            customTabActivityHelper.unbindCustomTabsService(getApplication())
        } catch (ex: Exception) {

        }
    }

    fun fetchStory(story: Story = Story.TOP) {
        if (storyIds == null) {
            hackerNewsStoryRepository.fetchStoryIds(story) {
                when (it) {
                    is Success -> {
                        storyIds = it.value
                        this.story = story
                        fetchItemDetails(it.value)
                    }
                    is Failure -> {
                        when (it.error) {
                            is NetworkError -> {
                                setNoInternetRetry {
                                    fetchStory(story)
                                }
                            }
                            is ResponseErrorWithCache -> {
                                showToast(it.error.responseBody)
                            }
                            is OtherError -> {
                                showToast(it.error.reason)
                            }
                        }
                    }
                }
            }
        }
    }

    fun retryFetchingStory() {
        when {
            repositoryResult.value?.retry != null -> repositoryResult.value?.retry?.invoke()
            story != null -> fetchStory(story!!)
            else -> showToast(getString(R.string.unable_to_retry))
        }
    }

    fun refreshStory() {
        when {
            repositoryResult.value?.refresh != null -> repositoryResult.value?.refresh?.invoke()
            story != null -> fetchStory(story!!)
            else -> showToast(getString(R.string.unable_to_refresh))
        }
    }

    fun getSelectedItem(position: Int) = repositoryResult.value?.pagedList?.value?.get(position)

    fun openUrl(url: String?) {
        if (url.isNullOrBlank()) {
        } else {
            val resources = getApplication<Application>().resources
            val backDrawable = ResourcesCompat
                .getDrawable(
                    resources,
                    R.drawable.ic_arrow_back,
                    getApplication<Application>().theme
                )
            backDrawable?.setCompatTint(getApplication<Application>().getCompatColor(android.R.color.white))
            val customTabsIntent = CustomTabsIntent.Builder(customTabActivityHelper.getSession())
                .setShowTitle(true)
                .setToolbarColor(getApplication<Application>().getCompatColor(R.color.colorPrimary))
            if (backDrawable != null) {
                customTabsIntent.setCloseButtonIcon(backDrawable.toBitmap())
            }
            getApplication<Application>().openCustomTab(customTabsIntent.build(), Uri.parse(url))
        }
    }

    fun fetchItemDetails(ids: List<Int>) {
        repositoryResult.value = hackerNewsItemDataSourceFetcher.getStoriesFromIds(ids)
    }
}