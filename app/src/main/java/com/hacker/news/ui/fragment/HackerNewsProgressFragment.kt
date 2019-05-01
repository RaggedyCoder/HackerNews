package com.hacker.news.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.hacker.news.R
import kotlinx.android.synthetic.main.fragment_hacker_news_progress.*
import kotlinx.android.synthetic.main.fragment_hacker_news_progress.view.*

abstract class HackerNewsProgressFragment : HackerNewsBaseFragment() {

    companion object {
        private const val MIN_SHOW_TIME = 750L // ms
        private const val MIN_DELAY = 750L // ms
    }

    private var mStartTime: Long = -1

    private var mPostedHide = false

    private var mPostedShow = false

    private var mDismissed = false

    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        contentViewHolder.isVisible = false
        progressViewHolder.isVisible = true
    }

    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            contentViewHolder.isVisible = true
            progressViewHolder.isVisible = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        removeCallbacks()
    }

    override fun onDetach() {
        super.onDetach()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        if (view != null) {
            view?.removeCallbacks(mDelayedHide)
            view?.removeCallbacks(mDelayedShow)
        }
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hacker_news_progress, container, false).apply {
            if (this is FrameLayout) {
                this.contentViewHolder.addView(onCreateContentView(inflater, this, savedInstanceState))
            }
        }
    }

    open fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View? =
        null

    fun setProgressText(@StringRes progressTextId: Int) {
        setProgressText(getText(progressTextId))
    }

    fun setProgressText(progressText: CharSequence) {
        setProgressText(progressText, TextView.BufferType.SPANNABLE)
    }

    fun setProgressText(progressText: CharSequence, type: TextView.BufferType) {
        this.progressTextView.setText(progressText, type)
    }

    /**
     * Hide the content view if it is visible. The content view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * content view was not yet visible, cancels showing the content view.
     */
    @Synchronized
    fun hideContentView() {
        if (view != null) {
            mDismissed = true

            view?.removeCallbacks(mDelayedShow)
            mPostedShow = false
            val diff = System.currentTimeMillis() - mStartTime
            if (diff >= MIN_SHOW_TIME || mStartTime == -1L) {
                // The container view has been shown long enough
                // OR was not shown yet. If it wasn't shown yet,
                // it will just never be shown.
                contentViewHolder.isVisible = false
                progressViewHolder.isVisible = true
            } else {
                // The container view is shown, but not long enough,
                // so put a delayed message in to hide it when its been
                // shown long enough.
                if (!mPostedHide) {
                    view?.postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
                    mPostedHide = true
                }
            }
        }
    }

    /**
     * Show the content view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    @Synchronized
    fun showContentView() {
        // Reset the start time.
        if (view != null) {
            mStartTime = -1
            mDismissed = false

            view?.removeCallbacks(mDelayedHide)
            mPostedHide = false
            if (!mPostedShow) {
                view?.postDelayed(mDelayedShow, MIN_DELAY)
                mPostedShow = true
            }
        }
    }
}