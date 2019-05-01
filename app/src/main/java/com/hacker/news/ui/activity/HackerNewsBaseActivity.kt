package com.hacker.news.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hacker.news.R
import com.hacker.news.util.*
import com.hacker.news.viewmodel.HackerNewsBaseViewModel
import com.hacker.news.viewmodel.factory.HackerNewsViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_hacker_news_base.*
import javax.inject.Inject

abstract class HackerNewsBaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelProviderFactory: HackerNewsViewModelFactory

    private val activityViewModel: HackerNewsBaseViewModel by lazy {
        createViewModel(getViewModelClass())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeNoNetworkAlert()
        observeActivityAction()
        observeAlert()
    }

    override fun supportFragmentInjector() = fragmentInjector

    private fun createViewModel(clazz: Class<out HackerNewsBaseViewModel>): HackerNewsBaseViewModel {
        return ViewModelProviders.of(this, viewModelProviderFactory)[clazz]
    }

    abstract fun getViewModelClass(): Class<out HackerNewsBaseViewModel>

    @Suppress("UNCHECKED_CAST")
    protected fun <T : HackerNewsBaseViewModel> getViewModel() = activityViewModel as T


    override fun setContentView(view: View?) {
        super.setContentView(R.layout.activity_hacker_news_base)
        containerView.addView(view)
        retryButton.setOnClickListener {
            activityViewModel.performNoInternetFailedAction()
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_hacker_news_base)
        val view = layoutInflater.inflate(layoutResID, containerView, false)
        containerView.addView(view)
        retryButton.setOnClickListener {
            activityViewModel.performNoInternetFailedAction()
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(R.layout.activity_hacker_news_base)
        containerView.addView(view, params)
        retryButton.setOnClickListener {
            activityViewModel.performNoInternetFailedAction()
        }
    }

    private fun observeNoNetworkAlert() {
        activityViewModel.internetConnectionLiveData.observe(this, Observer {
            containerView.isVisible = it == true
            noInternetConnectionMessageViewHolder.isVisible = it == false
        })
    }

    private fun observeActivityAction() {
        activityViewModel.activityActionLiveData.observe(this, Observer { actionData ->
            actionData?.let {
                when (it) {
                    is SwitchActivityActionData -> {
                        val (intent, shouldFinish) = it
                        startActivity(intent)
                        if (shouldFinish) {
                            finish()
                        }
                    }
                    is SwitchActivityForResultActionData -> {
                        val (requestCode, intent, shouldFinish) = it
                        startActivityForResult(intent, requestCode)
                        if (shouldFinish) {
                            finish()
                        }
                    }
                    is FinishActivityActionData -> {
                        val (resultCode, intent) = it
                        setResult(resultCode, intent)
                        finish()
                    }
                }
            }
        })
    }

    private fun observeAlert() {
        activityViewModel.alertLiveData.observe(this, Observer { alertData ->
            alertData?.let {
                when (it) {
                    is ToastAlert -> {
                        showToast(it.message, it.duration)
                    }
                    is SnackbarAlert -> {
                        showSnackbar(it.message, it.duration, it.action)
                    }
                    is DialogAlert -> {
                        dialog {
                            setTitle(it.title)
                            setMessage(it.message)
                            setPositiveButton(it.positiveAction?.title) { _, _ ->
                                it.positiveAction?.action?.invoke()
                            }
                            setNegativeButton(it.negativeAction?.title) { _, _ ->
                                it.negativeAction?.action?.invoke()
                            }
                            setCancelable(false)
                        }.show()
                    }
                }
            }
        })
    }
}