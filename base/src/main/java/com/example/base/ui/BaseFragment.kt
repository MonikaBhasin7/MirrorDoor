package com.example.base.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.base.R
import com.example.base.utils.AppLogger
import com.example.base.utils.CommonUtils
import com.example.base.views.PwRelativeLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private val TAG = BaseFragment::class.java.simpleName

    lateinit var mActivity: Activity
    lateinit var mView: View

    private lateinit var mBuilder: AlertDialog.Builder

    private lateinit var pwRelativeLayout: PwRelativeLayout
    lateinit var childView: View

    private lateinit var ivError: ImageView
    private lateinit var tvErrorTitle: TextView
    private lateinit var tvErrorMessagePrimary: TextView
    private lateinit var tvErrorMessageSecondary: TextView
    private lateinit var btRetry: Button

    /**
     *   Events are exposed to client fragment using these abstract methods
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onInitViews(view: View)


    override fun onCreate(savedInstanceState: Bundle?) {
        AppLogger.logCurrentMethodName(TAG)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as Activity
        //progress = ProgressDialogUtil(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        AppLogger.logCurrentMethodName(TAG)
        constructView(inflater, container)
        onInitViews(mView)

        return mView
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun constructView(inflater: LayoutInflater, container: ViewGroup?) {

        mView = inflater.inflate(R.layout.fragment_base, container, false)

        pwRelativeLayout = mView.findViewById(R.id.layout_fragment_root)

        childView = inflater.inflate(getLayoutId(), container, false)

        pwRelativeLayout.addView(childView)

        ivError = pwRelativeLayout.ivError
        tvErrorTitle = pwRelativeLayout.tvErrorTitle
        tvErrorMessagePrimary = pwRelativeLayout.tvErrorMessagePrimary
        tvErrorMessageSecondary = pwRelativeLayout.tvErrorMessageSecondary
        btRetry = pwRelativeLayout.btRetry

    }

    fun showLoading(loadingMessage: String= "Loading ...") {
        childView.visibility = View.GONE
        var message = loadingMessage
        if (loadingMessage == null)
            message = "Please wait..."

        pwRelativeLayout.showLoading(message!!)
    }

    fun hideLoading() {
        childView.visibility = View.VISIBLE
        pwRelativeLayout.hideLoading()
    }

    fun showNoDataScreen(
            drawableId: Int = R.drawable.icon_no_connection,
            title: String,
            message: String,
            retryButton: Boolean = false
    ) {
        pwRelativeLayout.showMessageWithImage(drawableId, title, message, retryButton)
        childView.visibility = View.GONE
    }

    fun showNoDataScreen(title: String, message: String, retryListener: View.OnClickListener) {
        pwRelativeLayout.showMessageWithImage(title, message, retryListener)
        childView.visibility = View.GONE
    }

    fun showNoDataScreenWithoutImage(
            title: String,
            message: String,
            retryButton: Boolean = false,
            onRetryClick: View.OnClickListener
    ) {
        pwRelativeLayout.showMessageWithoutImage(title, message, retryButton, onRetryClick)
        childView.visibility = View.GONE
    }

    fun hideNoDataScreen() {
        pwRelativeLayout.hideMessageWithImage()
        childView.visibility = View.VISIBLE
    }

    fun hideNoDataScreenWithoutImage() {
        pwRelativeLayout.hideMessageWithoutImage()
        childView.visibility = View.VISIBLE
    }

    /**
     *  Show a message dialog to user
     */
    fun showMessageDialog(title: String, message: String, buttonTitle: String, isCancelable :Boolean = true) {
        mBuilder = AlertDialog.Builder(mActivity)
        mBuilder.setCancelable(isCancelable)
        CommonUtils.showMessageDialog(mBuilder, title, message, buttonTitle)
    }
    /**
     * Hiding keyboard when page scrolls so that it doesn't unnecessarily cover screen space when not required.
     */
    fun hideKeyboard() {
        CommonUtils.hideKeyboard(mActivity)
    }

    fun showProgressDialog(){
        (activity as BaseActivity).showLoading(true)
        /* progress?.let {
             it.setProgressDialog()
         }*/
    }

    fun hideProgessDialog(){
        (activity as BaseActivity).showLoading(false)

        /*progress?.let {
            it.dismiss()
        }*/
    }
    override val coroutineContext: CoroutineContext = Dispatchers.IO

}