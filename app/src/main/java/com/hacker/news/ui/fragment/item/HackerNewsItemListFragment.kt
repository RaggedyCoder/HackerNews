package com.hacker.news.ui.fragment.item

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.hacker.news.R
import com.hacker.news.model.ItemKids
import com.hacker.news.ui.adapter.HackerNewsItemAdapter
import com.hacker.news.ui.adapter.HackerNewsItemAdapter.ClickActionType.*
import com.hacker.news.ui.fragment.HackerNewsProgressFragment
import com.hacker.news.util.NetworkState
import com.hacker.news.viewmodel.item.HackerNewsItemListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_hacker_news_item_list.*

class HackerNewsItemListFragment : HackerNewsProgressFragment() {

    override fun getViewModelClass() = HackerNewsItemListViewModel::class.java

    private lateinit var hackerNewsItemAdapter: HackerNewsItemAdapter

    private val hackerNewsItemListViewModel by lazy {
        getViewModel() as HackerNewsItemListViewModel
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_hacker_news_item_list, container, false)!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        lifecycle.addObserver(hackerNewsItemListViewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val navArgs = HackerNewsItemListFragmentArgs.fromBundle(arguments!!)
            if (navArgs.storyName != null) {
                setProgressText(R.string.fetching_stories)
                hideContentView()
                hackerNewsItemListViewModel.fetchStory(navArgs.storyName)
            } else if (navArgs.itemKids != null) {
                hackerNewsItemListViewModel.fetchItemDetails(navArgs.itemKids.kids)
            }
        } else {
            setProgressText(R.string.fetching_stories)
            hideContentView()
            hackerNewsItemListViewModel.fetchStory()
        }

        hackerNewsItemAdapter = HackerNewsItemAdapter({
            hackerNewsItemListViewModel.retryFetchingStory()
        }
        ) { position, clickActionType ->
            val item = hackerNewsItemListViewModel.getSelectedItem(position) ?: return@HackerNewsItemAdapter
            when (clickActionType) {
                SHOW_PROFILE -> {
                    if (item.by != null) {
                        view.findNavController().navigate(
                            HackerNewsItemListFragmentDirections.actionToGoHackerNewsUserProfileFragment(item.by)
                        )
                    }
                }
                OPEN_URL -> hackerNewsItemListViewModel.openUrl(item.url)
                SHOW_DESCRIPTION -> {
                    view.findNavController()
                        .navigate(
                            HackerNewsItemListFragmentDirections.actionToGoHackerNewsItemDescriptionFragment(item)
                        )
                }
                SHOW_POLLS -> {
                    if (item.parts != null)
                        view.findNavController()
                            .navigate(
                                HackerNewsItemListFragmentDirections.actionToGoHackerNewsItemListFragment(
                                    itemKids = ItemKids(item.parts)
                                )
                            )
                }
                SHOW_COMMENTS -> {
                    if (item.kids != null)
                        view.findNavController()
                            .navigate(
                                HackerNewsItemListFragmentDirections.actionToGoHackerNewsItemListFragment(
                                    itemKids = ItemKids(item.kids)
                                )
                            )
                }
            }
        }

        hackerNewsItemRecyclerView.adapter = hackerNewsItemAdapter
        hackerNewsItemListViewModel.pagedData.observe(this, Observer {
            hackerNewsItemAdapter.submitList(it)
            showContentView()
        })

        hackerNewsItemListViewModel.networkState.observe(this, Observer {
            hackerNewsItemAdapter.setNetworkState(it)
            hackerNewsItemSwipeRefreshLayout.isRefreshing = it == NetworkState.REFRESHING
        })

        hackerNewsItemListViewModel.refreshState.observe(this, Observer {
            hackerNewsItemSwipeRefreshLayout.isRefreshing = it == NetworkState.REFRESHING
        })

        hackerNewsItemSwipeRefreshLayout.setOnRefreshListener {
            hackerNewsItemListViewModel.refreshStory()
        }
    }
}