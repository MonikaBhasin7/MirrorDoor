package com.example.base.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.base.utils.AppLogger
import com.example.base.utils.CommonUtils
import com.example.base.utils.hideKeyboard

abstract class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName
    protected val loadingDialog by lazy { PageLoadingProgress(this) }
    private lateinit var mBuilder: AlertDialog.Builder
    lateinit var mAlertDialog: AlertDialog
    @LayoutRes
    abstract fun getLayoutId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        AppLogger.infoLog(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    /**
    *   makes activity full screen
    */
    fun makeActivityFullScreen() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    /**
     * Show Fragment
     * @param containerId where fragment should be added
     * @param fragment to be displayed
     * @param addToStack boolean for adding transaction in backStack
     *
     */
    fun showFragment(@IdRes containerId: Int, fragment: Fragment?, addToStack: Boolean) {
        AppLogger.logCurrentMethodName(TAG)
        if (fragment != null) {
            AppLogger.infoLog(TAG, "Show fragment " + fragment.javaClass.simpleName)
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(
//            R.anim.slide_in_left, R.anim.slide_out_left,
//            R.anim.slide_out_right, R.anim.slide_in_right
//        )

            if (addToStack) {
                fragmentTransaction.replace(containerId, fragment)
                fragmentTransaction.addToBackStack(null)
            } else {
                fragmentTransaction.replace(containerId, fragment)
            }
            fragmentTransaction.commit()
        } else {
            AppLogger.errorLog(TAG, "Fragment intance is NULL")
        }
    }

    fun showAddFragment(@IdRes containerId: Int, fragment: Fragment?, addToStack: Boolean) {
        AppLogger.logCurrentMethodName(TAG)
        if (fragment != null) {
            AppLogger.infoLog(TAG, "Show fragment " + fragment.javaClass.simpleName)
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(
//            R.anim.slide_in_left, R.anim.slide_out_left,
//            R.anim.slide_out_right, R.anim.slide_in_right
//        )

            if (addToStack) {
                fragmentTransaction.add(containerId, fragment)
                fragmentTransaction.addToBackStack(null)
            } else {
                fragmentTransaction.add(containerId, fragment)
            }
            fragmentTransaction.commit()
        } else {
            AppLogger.errorLog(TAG, "Fragment intance is NULL")
        }
    }

    fun popFragment() {
        supportFragmentManager.popBackStack()
    }

    /**
     *  Show a message dialog to user
     */
    fun showMessageDialog(title: String, message: String, buttonTitle: String) {
        mBuilder = AlertDialog.Builder(this)
        CommonUtils.showMessageDialog(mBuilder, title, message, buttonTitle)
    }

    fun showActionDialog(
            title: String,
            message: String,
            actionListener: DialogInterface.OnClickListener
    ) {
        CommonUtils.showActionDialog(AlertDialog.Builder(this), title, message, actionListener)
    }

    /**
     *  Set toolbar title
     */
    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    /**
     *  Adds up button to activity
     */
    fun enableUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showLoading(isShow: Boolean) {
        if (isShow) {
            with(loadingDialog) {
                showFromActivity(this@BaseActivity)
                requestFocus()
            }
            hideKeyboard()
        } else loadingDialog.hideFromActivity(this)
    }
}