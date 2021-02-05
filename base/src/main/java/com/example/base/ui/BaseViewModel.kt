package com.example.base.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.models.ApiResponseWrapper
import com.example.base.utils.AppLogger

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName

    private var mLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean> get() = mLoadingLiveData

    private fun loading(isLoading: Boolean) {
        mLoadingLiveData.value = isLoading
    }

    fun startLoading() {
        AppLogger.logCurrentMethodName(TAG)
        loading(true)
    }

    fun stopLoading() {
        AppLogger.logCurrentMethodName(TAG)
        loading(false)
    }

    fun <T> updateLiveData(data: T?, liveData: MutableLiveData<ApiResponseWrapper<T>>) {
        val dataWrapper = ApiResponseWrapper<T>()
        dataWrapper.data = data
        liveData.value = dataWrapper
    }

    fun <T> updateLiveDataWithError(
        error: String,
        liveData: MutableLiveData<ApiResponseWrapper<T>>
    ) {
        val dataWrapper = ApiResponseWrapper<T>()
        dataWrapper.errorMessage = error
        liveData.value = dataWrapper
    }
}