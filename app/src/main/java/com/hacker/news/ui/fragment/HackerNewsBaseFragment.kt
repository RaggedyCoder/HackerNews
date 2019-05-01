package com.hacker.news.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hacker.news.ui.activity.HackerNewsBaseActivity
import com.hacker.news.util.*
import com.hacker.news.viewmodel.HackerNewsBaseViewModel
import com.hacker.news.viewmodel.factory.HackerNewsViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class HackerNewsBaseFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelProviderFactory: HackerNewsViewModelFactory

    private val fragmentViewModel: HackerNewsBaseViewModel by lazy {
        createViewModel(getViewModelClass())
    }

    private val activityViewModel: HackerNewsBaseViewModel by lazy {
        if (activity is HackerNewsBaseActivity) {
            ViewModelProviders.of(activity!!)[(activity as HackerNewsBaseActivity).getViewModelClass()]
        } else {
            throw RuntimeException("Base activity isn't a subclass of HackerNewsBaseActivity")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeNoNetworkAlert()
        observeAlert()
        observeActivityAction()
    }

    override fun supportFragmentInjector() = fragmentInjector

    private fun createViewModel(clazz: Class<out HackerNewsBaseViewModel>): HackerNewsBaseViewModel {
        return ViewModelProviders.of(this, viewModelProviderFactory)[clazz]
    }

    abstract fun getViewModelClass(): Class<out HackerNewsBaseViewModel>

    @Suppress("UNCHECKED_CAST")
    protected fun <T : HackerNewsBaseViewModel> getViewModel() = fragmentViewModel as T

    private fun observeNoNetworkAlert() {
        fragmentViewModel.internetConnectionLiveData.observe(this, Observer {
            if (it == false) {
                activityViewModel.setNoInternetRetry {
                    fragmentViewModel.performNoInternetFailedAction()
                }
            }
        })
    }

    private fun observeActivityAction() {
        fragmentViewModel.activityActionLiveData.observe(this, Observer { actionData ->
            actionData?.let {
                when (it) {
                    is SwitchActivityActionData -> {
                        val (intent, shouldFinish) = it
                        startActivity(intent)
                        if (shouldFinish) {
                            activity?.finish()
                        } else {
                            // nothing to do
                        }
                    }
                    is SwitchActivityForResultActionData -> {
                        val (requestCode, intent, shouldFinish) = it
                        startActivityForResult(intent, requestCode)
                        if (shouldFinish) {
                            activity?.finish()
                        } else {
                            // nothing to do
                        }
                    }
                    is FinishActivityActionData -> {
                        val (resultCode, intent) = it
                        activity?.setResult(resultCode, intent)
                        activity?.finish()
                    }
                }
            }
        })
    }

    private fun observeAlert() {
        fragmentViewModel.alertLiveData.observe(this, Observer { alertData ->
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
                        }?.show()
                    }
                }
            }
        })
    }
}