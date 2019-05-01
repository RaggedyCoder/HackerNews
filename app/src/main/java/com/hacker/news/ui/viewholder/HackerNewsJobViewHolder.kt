package com.hacker.news.ui.viewholder

import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.core.view.isVisible
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.ui.adapter.HackerNewsItemAdapter
import kotlinx.android.synthetic.main.list_item_hacker_news_job.view.*

class HackerNewsJobViewHolder private constructor(
    itemView: View,
    onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
) :
    BaseViewHolder<ItemInfoResponse>(itemView) {

    companion object {
        fun create(
            parent: ViewGroup,
            onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
        ) =
            HackerNewsJobViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_hacker_news_job, parent, false),
                onClickAction
            )
    }

    init {
        itemView.openLinkButton.setOnClickListener {
            onClickAction(adapterPosition, HackerNewsItemAdapter.ClickActionType.OPEN_URL)
        }
    }

    override fun bindView(model: ItemInfoResponse) {
        itemView.titleTextView.setText(generateTitle(model.title, model.url), TextView.BufferType.SPANNABLE)
        if (model.time != null) {
            itemView.publishTimeTextView.setText(
                DateUtils.getRelativeTimeSpanString(
                    model.time.time,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
                ),
                TextView.BufferType.SPANNABLE
            )
        }

        itemView.openLinkButton.isVisible = !model.url.isNullOrBlank()
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
}