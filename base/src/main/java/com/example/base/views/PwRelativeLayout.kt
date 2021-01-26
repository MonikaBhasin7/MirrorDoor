package com.example.base.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.base.R


class PwRelativeLayout : RelativeLayout {

    private lateinit var mLayoutInflater: LayoutInflater
    private lateinit var mContext: Context

    lateinit var rlNothing: RelativeLayout
    lateinit var ivError: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var tvLoadingMessage: TextView
    lateinit var tvErrorTitle: TextView
    lateinit var tvErrorMessagePrimary: TextView
    lateinit var tvErrorMessageSecondary: TextView
    lateinit var btRetry: Button

    constructor(context: Context) : super(context) {
        init(context)

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        mLayoutInflater = LayoutInflater.from(context)
        val view: View = mLayoutInflater.inflate(R.layout.pw_relative_layout, this, false)
        initViews(view)
        this.addView(view)
    }

    private fun initViews(view: View) {

        rlNothing = view.findViewById(R.id.layout_nothing)

        ivError = view.findViewById(R.id.iv_error)

        progressBar = view.findViewById(R.id.progress_bar)
        tvLoadingMessage = view.findViewById(R.id.tv_loading_message)

        tvErrorTitle = view.findViewById(R.id.tv_error_title)
        tvErrorMessagePrimary = view.findViewById(R.id.tv_error_message_primary)
        tvErrorMessageSecondary = view.findViewById(R.id.tv_error_message_secondary)
        btRetry = view.findViewById(R.id.bt_retry)
    }

    fun show() {
        rlNothing.visibility = View.VISIBLE
    }

    fun hide() {
        rlNothing.visibility = View.GONE
    }

    fun showLoading(message: String) {
        // show main layout
        show()

        // Hide no connection related widgets
        toggleNoConnectionWidgets(View.GONE)

        // show loading widgets
        toggleLoadingWidgets(View.VISIBLE)

        // set loading text
        tvLoadingMessage.setText(message)

    }

    fun hideLoading() {
        // show main layout
        show()

        // Hide loading widgets
        toggleLoadingWidgets(View.GONE)

        // Hide no connection related widgets
        toggleNoConnectionWidgets(View.GONE)

    }

    fun showMessageWithImage(
            drawableId: Int = R.drawable.icon_no_connection,
            title: String,
            message: String,
            retryButton: Boolean = false
    ) {
        // show main layout
        show()

        // show no connection widgets
        toggleNoConnectionWidgets(View.VISIBLE)

        // hide loading widgets
        toggleLoadingWidgets(View.GONE)


        tvErrorTitle.setText(title)
        ivError.setImageDrawable(ContextCompat.getDrawable(context, drawableId))
        tvErrorMessageSecondary.setText(message)

        if (retryButton) {
            btRetry.visibility = View.VISIBLE
        } else {
            btRetry.visibility = View.GONE
        }
    }

    fun showMessageWithImage(title: String, message: String, retryButtonListener: OnClickListener) {
        // show main layout
        show()

        // show no connection widgets
        toggleNoConnectionWidgets(View.VISIBLE)

        // hide loading widgets
        toggleLoadingWidgets(View.GONE)


        tvErrorTitle.setText(title)
        ivError.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_no_connection))
        tvErrorMessageSecondary.setText(message)

        btRetry.setOnClickListener(retryButtonListener)
    }

    fun showMessageWithoutImage(
            title: String,
            message: String,
            retryButton: Boolean = false,
            onRetryClick: OnClickListener
    ) {
        // show main layout
        show()

        // show no connection widgets
        toggleNoConnectionWidgets(View.VISIBLE)

        // hide loading widgets
        toggleLoadingWidgets(View.GONE)


        tvErrorTitle.setText(title)
        ivError.visibility = View.GONE
        tvErrorMessageSecondary.setText(message)

        if (retryButton) {
            btRetry.visibility = View.VISIBLE
        } else {
            btRetry.visibility = View.GONE
        }
        btRetry.setOnClickListener(onRetryClick)
    }



    fun hideMessageWithoutImage() {
        hideLoading()
    }

    fun hideMessageWithImage() {
        hideLoading()
    }


    private fun toggleNoConnectionWidgets(visibility: Int) {
        ivError.visibility = visibility
        tvErrorTitle.visibility = visibility
        // tvErrorMessagePrimary.visibility = visibility
        tvErrorMessageSecondary.visibility = visibility
        btRetry.visibility = visibility
    }

    private fun toggleLoadingWidgets(visibility: Int) {
        progressBar.visibility = visibility
        tvLoadingMessage.visibility = visibility
    }
}