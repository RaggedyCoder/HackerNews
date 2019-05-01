package com.hacker.news.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hacker.news.R
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.ui.viewholder.*
import com.hacker.news.util.NetworkState

class HackerNewsItemAdapter(
    private val retryCallback: () -> Unit,
    private val onClickAction: (position: Int, clickActionType: ClickActionType) -> Unit
) :
    PagedListAdapter<ItemInfoResponse, BaseViewHolder<*>>(ITEM_DIFF_CALLBACK) {

    companion object {
        private val ITEM_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemInfoResponse>() {
            override fun areItemsTheSame(oldItem: ItemInfoResponse, newItem: ItemInfoResponse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemInfoResponse, newItem: ItemInfoResponse) = oldItem == newItem
        }
    }

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.list_item_hacker_news_story -> HackerNewsItemStoryViewHolder.create(parent, onClickAction)
        R.layout.list_item_hacker_news_comment -> HackerNewsCommentViewHolder.create(parent, onClickAction)
        R.layout.list_item_hacker_news_ask -> HackerNewsItemAskViewHolder.create(parent, onClickAction)
        R.layout.list_item_hacker_news_job -> HackerNewsJobViewHolder.create(parent, onClickAction)
        R.layout.list_item_hacker_news_poll -> HackerNewsItemPollViewHolder.create(parent, onClickAction)
        R.layout.list_item_hacker_news_poll_options -> HackerNewsPollOptionsViewHolder.create(parent, onClickAction)
        R.layout.list_item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
        else -> throw IllegalArgumentException("unknown view type $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HackerNewsItemStoryViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is HackerNewsCommentViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is HackerNewsItemAskViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is HackerNewsJobViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is HackerNewsItemPollViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is HackerNewsPollOptionsViewHolder -> {
                val model = getItem(position) ?: return
                holder.bindView(model)
            }
            is NetworkStateViewHolder -> {
                if (networkState != null) {
                    holder.bindView(networkState!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_network_state
        } else {
            when (getItem(position)?.type) {
                "story" -> R.layout.list_item_hacker_news_story
                "comment" -> R.layout.list_item_hacker_news_comment
                "job" -> R.layout.list_item_hacker_news_job
                "ask" -> R.layout.list_item_hacker_news_ask
                "poll" -> R.layout.list_item_hacker_news_poll
                "pollopt" -> R.layout.list_item_hacker_news_poll_options
                else -> throw java.lang.IllegalArgumentException("unknown item type ${getItem(position)?.type}")
            }
        }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyDataSetChanged()
        }
    }

    private fun hasExtraRow() =
        networkState != null && networkState != NetworkState.REFRESHING && networkState != NetworkState.LOADED

    override fun getItemCount() = super.getItemCount() + if (hasExtraRow()) 1 else 0

    enum class ClickActionType {
        SHOW_PROFILE, SHOW_DESCRIPTION, SHOW_COMMENTS, SHOW_POLLS, OPEN_URL
    }
}