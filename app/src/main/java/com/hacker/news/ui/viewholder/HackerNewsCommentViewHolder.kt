package com.hacker.news.ui.viewholder

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.ui.adapter.HackerNewsItemAdapter
import kotlinx.android.synthetic.main.list_item_hacker_news_comment.view.*

class HackerNewsCommentViewHolder private constructor(
    itemView: View,
    onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
) :
    BaseViewHolder<ItemInfoResponse>(itemView) {

    companion object {
        fun create(
            parent: ViewGroup,
            onClickAction: (position: Int, clickActionType: HackerNewsItemAdapter.ClickActionType) -> Unit
        ) =
            HackerNewsCommentViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_hacker_news_comment, parent, false),
                onClickAction
            )
    }

    init {
        itemView.showRepliesButton.setOnClickListener {
            onClickAction(adapterPosition, HackerNewsItemAdapter.ClickActionType.SHOW_COMMENTS)
        }

        itemView.userNameTextView.setOnClickListener {
            onClickAction(adapterPosition, HackerNewsItemAdapter.ClickActionType.SHOW_PROFILE)
        }

    }

    override fun bindView(model: ItemInfoResponse) {
        if (model.by != null) {
            itemView.userNameTextView.text = model.by
        }
        if (model.time != null) {
            itemView.publishTimeTextView.text =
                DateUtils.getRelativeTimeSpanString(
                    model.time.time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS
                )
        }
        if (!model.text.isNullOrBlank()) {
            itemView.commentTextView.text = HtmlCompat.fromHtml(model.text, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        if (model.kids.isNullOrEmpty()) {
            itemView.showRepliesButton.text =
                itemView.context.resources.getQuantityString(R.plurals.replies, 0, 0)
            itemView.showRepliesButton.isEnabled = false
        } else {
            itemView.showRepliesButton.text =
                itemView.context.resources.getQuantityString(R.plurals.replies, model.kids.size, model.kids.size)
            itemView.showRepliesButton.isEnabled = true
        }
    }
}