package com.hacker.news.viewmodel.profile

import android.app.Application
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hacker.news.R
import com.hacker.news.api.response.UserInfoResponse
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER
import com.hacker.news.model.*
import com.hacker.news.repository.HackerNewsUserProfileRepository
import com.hacker.news.viewmodel.HackerNewsBaseViewModel
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Named

class HackerNewsUserProfileViewModel @Inject constructor(
    private val hackerNewsUserProfileRepository: HackerNewsUserProfileRepository,
    @Named(QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER) val defaultNumberFormatter: NumberFormat,
    application: Application
) :
    HackerNewsBaseViewModel(application) {

    private val userInfoLiveData = MutableLiveData<UserInfoResponse>()

    val userInfoUILiveData = Transformations.map(userInfoLiveData) { userInfo ->
        userInfo?.let {
            val title = generateTitle(it.id, it.karma)
            val createdTime = getString(
                R.string.user_created_time_holder, DateUtils.getRelativeTimeSpanString(
                    it.created.time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS
                )
            )
            val about = HtmlCompat.fromHtml(it.about ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
            val showSubmissionText = if (it.submitted.isNullOrEmpty()) {
                getString(R.string.no_submissions)
            } else {
                getApplication<Application>().resources.getQuantityString(
                    R.plurals.submissions, it.submitted.size,
                    defaultNumberFormatter.format(it.submitted.size)
                )
            }
            val isAnySubmissionAvailable = !it.submitted.isNullOrEmpty()
            val submittedItem = it.submitted ?: listOf()
            return@map UserInfoInfoUIData(
                title,
                createdTime,
                about,
                showSubmissionText,
                isAnySubmissionAvailable,
                submittedItem
            )
        }
    }

    private fun showUserProfile(userInfoResponse: UserInfoResponse) {
        this.userInfoLiveData.value = userInfoResponse
    }

    private fun generateTitle(id: String, karmaScore: Int): CharSequence {
        val titleBuilder = SpannableStringBuilder(id)
        if (id.isNotBlank()) {
            titleBuilder.append(" ")
        }
        titleBuilder.append("(karma:$karmaScore)")
        val startIndex = titleBuilder.indexOf("(karma:$karmaScore)")
        if (startIndex != -1) {
            val endIndex = startIndex + "(karma:$karmaScore)".length
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
        return titleBuilder.toSpannable()
    }

    fun fetchUserInfo(userId: String) {
        hackerNewsUserProfileRepository.fetchUserProfile(userId) {
            when (it) {
                is Success -> {
                    showUserProfile(it.value)
                }
                is Failure -> {
                    when (it.error) {
                        is NetworkError -> {
                            setNoInternetRetry {
                                fetchUserInfo(userId)
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

    data class UserInfoInfoUIData(
        val title: CharSequence,
        val createdTime: CharSequence,
        val about: CharSequence,
        val showSubmissionText: CharSequence,
        val isAnySubmissionAvailable: Boolean,
        val submittedItem: List<Int>
    )
}