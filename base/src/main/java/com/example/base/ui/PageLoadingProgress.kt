package com.example.base.ui

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.core.view.minusAssign
import androidx.core.view.plusAssign
import com.example.base.R

/**
 * A Class which is used to show a loader.
 * <p>This Class is a part of :ui Module and is currently being used inside the :base module to show loaders ib activity's and fragment.
 * This class extends a linearLayout class and inflates a xml
 * This Class's object is passed through a dependency injection technique (the one used here is koin)</p>
 * <p>The classes showFromActivity() method is called to show loader</p>
 */
class PageLoadingProgress : LinearLayout {

    constructor(context: Context?) : super(context)

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_page_loading_progress, this, true)
        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
    }

    /**
     * <p>This function is used to display loader</p>
     * @param activity This is activity's context from which this is called
     */
    fun showFromActivity(activity: AppCompatActivity) {
        val rootView = getViewGroup(activity)

        if (!rootView.contains(this)) rootView += this
    }

    /**
     * <p>This function is used to hide loader
     * @param activity This is activity's context from which this is called</p>
     */
    fun hideFromActivity(activity: AppCompatActivity) {
        val rootView = getViewGroup(activity)

        if (rootView.contains(this)) rootView -= this
    }

    /**
     * <p>This function returns a boolean specifying weather the loader is displayed or not</p>
     * @param activity This is activity's context from which this is called
     */
    fun isVisible(activity: AppCompatActivity) = getViewGroup(activity).contains(this)

    /**
     * <p>This function returns a ViewGroup on which the loader can be added to</p>
     * @param activity This is activity's context from which this is called
     */
    private fun getViewGroup(activity: AppCompatActivity): ViewGroup {
        return activity.window.decorView.findViewById(android.R.id.content) as ViewGroup
    }

}