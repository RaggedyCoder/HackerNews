package com.hacker.news.ui.fragment.profile

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
import com.hacker.news.ui.fragment.HackerNewsProgressFragment
import com.hacker.news.viewmodel.profile.HackerNewsUserProfileViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_hacker_news_user_profile.*

class HackerNewsUserProfileFragment : HackerNewsProgressFragment() {
    override fun getViewModelClass() = HackerNewsUserProfileViewModel::class.java

    private val hackerNewsUserProfileViewModel by lazy {
        getViewModel() as HackerNewsUserProfileViewModel
    }

    private val profileInfoArguments: HackerNewsUserProfileFragmentArgs by navArgs()

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_hacker_news_user_profile, container, false)!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProgressText(R.string.fetching_user_profile)
        hideContentView()
        hackerNewsUserProfileViewModel.fetchUserInfo(profileInfoArguments.userId)

        hackerNewsUserProfileViewModel.userInfoUILiveData.observe(this, Observer { userInfoUIData ->
            showContentView()
            userInfoUIData?.let {
                titleTextView.setText(it.title, TextView.BufferType.SPANNABLE)
                userCreatedDateTextView.setText(it.createdTime, TextView.BufferType.SPANNABLE)
                aboutTextView.setText(it.about, TextView.BufferType.SPANNABLE)
                showSubmittedItemsButton.setText(it.showSubmissionText, TextView.BufferType.SPANNABLE)
                showSubmittedItemsButton.isEnabled = it.isAnySubmissionAvailable
                showSubmittedItemsButton.isVisible = true
                showSubmittedItemsButton.setOnClickListener { button ->
                    button.findNavController().navigate(
                        HackerNewsUserProfileFragmentDirections.actionToGoHackerNewsItemListFragment(
                            ItemKids(
                                it.submittedItem
                            )
                        )
                    )
                }
            }
        })
    }
}