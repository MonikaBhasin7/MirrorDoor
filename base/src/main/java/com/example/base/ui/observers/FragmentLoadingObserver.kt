package ui.observers

import com.example.base.ui.BaseFragment


class FragmentLoadingObserver(private val mFragment: BaseFragment) : LoadingObserver() {

    override fun onLoadingStarted() {
        mFragment.showLoading("Please wait..")
    }

    override fun onLoadingStoped() {
       mFragment.hideLoading()
    }
}