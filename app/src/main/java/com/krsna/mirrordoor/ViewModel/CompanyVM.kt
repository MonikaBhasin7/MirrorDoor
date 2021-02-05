package com.krsna.mirrordoor.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.models.ApiResponseWrapper
import com.example.base.models.BaseResponse
import com.example.base.ui.BaseViewModel
import com.example.base.ui.BaseViewModelFactory
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.Repository.CompanyRepo

class CompanyVM(private val companyRepo: CompanyRepo, app: Application) : BaseViewModel(app) {

    private  var _addCompanyResponse: MutableLiveData<ApiResponseWrapper<BaseResponse>> =
            MutableLiveData()
    val addCompanyResponse: LiveData<ApiResponseWrapper<BaseResponse>> get() = _addCompanyResponse

    fun addCompany(company: Company) {
        companyRepo.addCompany(company, object :Callbacks.AddCompanyCallback{
            override fun companyAddedSuccess(response: BaseResponse) {
                updateLiveData(response, _addCompanyResponse)
            }

            override fun companyAddedFailure(error: String?) {
                if (error != null) {
                    updateLiveDataWithError(error, _addCompanyResponse)
                }
            }
        })
    }
}