package com.hacker.news.viewmodel.item

import android.app.Application
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.lifecycle.*
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER
import com.hacker.news.util.customtabs.helper.CustomTabActivityHelper
import com.hacker.news.util.customtabs.openCustomTab
import com.hacker.news.util.getCompatColor
import com.hacker.news.util.setCompatTint
import com.hacker.news.viewmodel.HackerNewsBaseViewModel
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class HackerNewsItemDescriptionViewModel @Inject constructor(
    private val customTabActivityHelper: CustomTabActivityHelper,
    @Named(QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER) private val defaultNumberFormatter: NumberFormat,
    application: Application
) :
    HackerNewsBaseViewModel(application), LifecycleObserver {

    private val itemInfoLiveData = MutableLiveData<ItemInfoResponse>()
    val itemInfoUILiveData = Transformations.map(itemInfoLiveData) { itemInfo ->
        itemInfo?.let {
            val title = generateTitle(it.title, it.url)
            val subtitle = generateSubTitle(it.score, it.by, it.time)
            val description = HtmlCompat.fromHtml(it.text ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
            val canOpenLink = !it.url.isNullOrBlank()
            val isPollOptionAvailable = !it.parts.isNullOrEmpty()
            val isShowCommentActionable = !it.kids.isNullOrEmpty()
            val commentButtonText = if (it.kids.isNullOrEmpty()) {
                getString(R.string.no_comments)
            } else {
                getApplication<Application>().resources.getQuantityString(
                    R.plurals.comments, it.kids.size,
                    defaultNumberFormatter.format(it.kids.size)
                )
            }
            return@map ItemInfoUIData(
                title,
                subtitle,
                description,
                isShowCommentActionable,
                commentButtonText,
                canOpenLink,
                isPollOptionAvailable
            )
        }
    }

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

    fun showItemDescription(item: ItemInfoResponse) {
        itemInfoLiveData.postValue(item)
    }

    fun openUrl() {
        val url = itemInfoLiveData.value?.url
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

    private fun generateTitle(title: String?, url: String?): CharSequence {
        if (title.isNullOrBlank()) {
            return getString(R.string.no_title_available)
        } else {
            val titleBuilder = SpannableStringBuilder(title)
            if (!url.isNullOrBlank()) {
                val hostName = Uri.parse(url).host
                titleBuilder.append(" ").append("($hostName)")
                val startIndex = titleBuilder.indexOf("($hostName)")
                if (startIndex != -1) {
                    val endIndex = startIndex + "($hostName)".length
                    titleBuilder.setSpan(
                        ForegroundColorSpan(Color.GRAY),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    titleBuilder.setSpan(
                        RelativeSizeSpan(0.75F),
                        startIndex,
                        endIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return titleBuilder.toSpannable()
        }
    }

    private fun generateSubTitle(score: Int?, userName: String?, date: Date?): CharSequence {
        val subTitleBuilder = SpannableStringBuilder()
        if (score != null) {
            subTitleBuilder.append(
                getApplication<Application>().resources.getQuantityString(
                    R.plurals.points,
                    score, defaultNumberFormatter.format(score)
                )
            )
        }
        if (userName != null) {
            if (score != null) {
                subTitleBuilder.append(" by ")
            }
            subTitleBuilder.append(userName)
            val startIndex = subTitleBuilder.indexOf(userName)
            if (startIndex != -1) {
                val endIndex = startIndex + userName.length
                subTitleBuilder.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        if (date != null) {
            if (score != null && userName != null) {
                subTitleBuilder.append(" ")
            }
            subTitleBuilder.append(
                DateUtils.getRelativeTimeSpanString(
                    date.time,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
                )
            )
        }
        return subTitleBuilder.toSpannable()
    }

    data class ItemInfoUIData(
        val title: CharSequence,
        val subTitle: CharSequence,
        val description: CharSequence,
        val isShowCommentActionable: Boolean,
        val commentButtonText: CharSequence,
        val canOpenLink: Boolean,
        val isPollOptionAvailable: Boolean
    )
}