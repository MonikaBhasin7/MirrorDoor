package com.example.base.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.base.utils.AppLogger
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseBottomSheetDialogFragment: BottomSheetDialogFragment(), CoroutineScope {

    private val TAG = BaseBottomSheetDialogFragment::class.java.simpleName
    lateinit var mActivity: Activity
    lateinit var childView: View

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun onInitViews(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AppLogger.logCurrentMethodName(TAG)
        constructView(inflater, container)
        onInitViews(childView)
        return childView
    }

    private fun constructView(inflater: LayoutInflater, container: ViewGroup?) {
        childView = inflater.inflate(getLayoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as Activity
    }

    override val coroutineContext: CoroutineContext = Dispatchers.IO
}