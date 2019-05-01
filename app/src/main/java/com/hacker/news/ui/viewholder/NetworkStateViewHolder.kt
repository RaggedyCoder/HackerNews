package com.hacker.news.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.gson.JsonParseException
import com.hacker.news.R
import com.hacker.news.util.CompleteState
import com.hacker.news.util.ErrorState
import com.hacker.news.util.NetworkState
import com.hacker.news.util.Status
import kotlinx.android.synthetic.main.list_item_network_state.view.*
import java.net.UnknownHostException

class NetworkStateViewHolder private constructor(itemView: View, retryCallback: () -> Unit) :
    BaseViewHolder<NetworkState>(itemView) {

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit) =
            NetworkStateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_network_state, parent, false),
                retryCallback
            )
    }


    init {
        itemView.retryButton.setOnClickListener {
            retryCallback()
        }
    }

    override fun bindView(model: NetworkState) {
        itemView.progressBar.isVisible = model.status == Status.RUNNING
        itemView.retryButton.isVisible = model.status == Status.FAILED
        when (model) {
            is ErrorState -> {
                itemView.messageTextView.setText(
                    when {
                        model.error is UnknownHostException -> R.string.no_internet_connection_message
                        model.error is JsonParseException -> R.string.unable_to_parse_server_message
                        else -> R.string.unknown_error
                    }
                )
                itemView.messageTextView.isVisible = true
            }
            is CompleteState -> {
                itemView.messageTextView.setText(R.string.no_more_items_available)
                itemView.messageTextView.isVisible = true
            }
            else -> {
                itemView.messageTextView.isVisible = false
            }
        }
    }
}