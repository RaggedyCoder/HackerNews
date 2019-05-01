package com.hacker.news.ui.fragment.item

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.hacker.news.R
import com.hacker.news.model.ItemKids
import com.hacker.news.ui.fragment.HackerNewsBaseFragment
import com.hacker.news.viewmodel.item.HackerNewsItemDescriptionViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_hacker_news_item_description.*

class HackerNewsItemDescriptionFragment : HackerNewsBaseFragment() {
    override fun getViewModelClass() = HackerNewsItemDescriptionViewModel::class.java

    private val hackerNewsItemDescriptionViewModel by lazy {
        getViewModel() as HackerNewsItemDescriptionViewModel
    }

    private val arguments: HackerNewsItemDescriptionFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_hacker_news_item_description, container, false)!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        lifecycle.addObserver(hackerNewsItemDescriptionViewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hackerNewsItemDescriptionViewModel.showItemDescription(arguments.itemInfo)

        hackerNewsItemDescriptionViewModel.itemInfoUILiveData.observe(this, Observer { itemInfoUI ->
            itemInfoUI?.let {
                titleTextView.setText(it.title, TextView.BufferType.SPANNABLE)
                subTitleTextView.setText(it.subTitle, TextView.BufferType.SPANNABLE)
                descriptionTextView.setText(it.description, TextView.BufferType.SPANNABLE)
                openLinkButton.isVisible = it.canOpenLink
                showPollOptionsButton.isVisible = it.isPollOptionAvailable
                showCommentsButton.isEnabled = it.isShowCommentActionable
                showCommentsButton.text = it.commentButtonText
            }
        })

        openLinkButton.setOnClickListener {
            hackerNewsItemDescriptionViewModel.openUrl()
        }

        subTitleTextView.setOnClickListener {
            if (arguments.itemInfo.by != null) {
                view.findNavController().navigate(
                    HackerNewsItemListFragmentDirections.actionToGoHackerNewsUserProfileFragment(arguments.itemInfo.by!!)
                )
            }
        }

        showCommentsButton.setOnClickListener {
            if (arguments.itemInfo.kids != null) {
                view.findNavController().navigate(
                    HackerNewsItemDescriptionFragmentDirections
                        .actionToGoHackerNewsItemListFragment(ItemKids(arguments.itemInfo.kids!!))
                )
            }
        }

        showPollOptionsButton.setOnClickListener {
            if (arguments.itemInfo.parts != null) {
                view.findNavController().navigate(
                    HackerNewsItemDescriptionFragmentDirections
                        .actionToGoHackerNewsItemListFragment(ItemKids(arguments.itemInfo.parts!!))
                )
            }
        }
    }
}