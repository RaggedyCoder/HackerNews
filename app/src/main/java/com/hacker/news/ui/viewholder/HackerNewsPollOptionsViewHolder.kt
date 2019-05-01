package com.hacker.news.ui.viewholder

import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.toSpannable
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.ui.adapter.HackerNewsItemAdapter
import kotlinx.android.synthetic.main.list_item_hacker_news_poll_options.view.*
import java.text.NumberFormat
import java.util.*

class HackerNewsPollOptionsViewHolder private constructor(
    itemView: View,
    onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
) :
    BaseViewHolder<ItemInfoResponse>(itemView) {

    private val scoreNumberFormatter = NumberFormat.getNumberInstance()

    companion object {
        fun create(
            parent: ViewGroup,
            onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
        ) =
            HackerNewsPollOptionsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_hacker_news_poll_options, parent, false),
                onClickAction
            )
    }

    init {
        itemView.setOnClickListener {
            onClickAction(adapterPosition, HackerNewsItemAdapter.ClickActionType.SHOW_DESCRIPTION)
        }
    }

    override fun bindView(model: ItemInfoResponse) {
        itemView.titleTextView.setText(generateTitle(model.title, model.url), TextView.BufferType.SPANNABLE)
        itemView.subTitleTextView.setText(
            generateSubTitle(model.score, model.by, model.time),
            TextView.BufferType.SPANNABLE
        )
    }

    private fun generateTitle(title: String?, url: String?): CharSequence {
        if (title.isNullOrBlank()) {
            return itemView.context.getString(R.string.no_title_available)
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
                itemView.resources.getQuantityString(
                    R.plurals.points,
                    score, scoreNumberFormatter.format(score)
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
}