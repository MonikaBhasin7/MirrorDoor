package com.krsna.mirrordoor.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.models.ApiResponseWrapper
import com.example.base.models.BaseResponse
import com.example.base.ui.BaseViewModel
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.Model.ShowCompanyPayload
import com.krsna.mirrordoor.Repository.CompanyRepo

class CompanyVM(private val companyRepo: CompanyRepo, app: Application) : BaseViewModel(app) {

    private  var _addCompanyResponse: MutableLiveData<ApiResponseWrapper<BaseResponse>> =
            MutableLiveData()
    val addCompanyResponse: LiveData<ApiResponseWrapper<BaseResponse>> get() = _addCompanyResponse

    private  var _showCompanyResponse: MutableLiveData<ApiResponseWrapper<List<Company>>> =
        MutableLiveData()
    val showCompanyResponse: LiveData<ApiResponseWrapper<List<Company>>> get() = _showCompanyResponse

    var filterReview : String? = null

    fun refreshLiveData() {
        _addCompanyResponse = MutableLiveData()
        _showCompanyResponse = MutableLiveData()
    }

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

    fun showCompanies() {
        var showCompanyPayload = ShowCompanyPayload().setReview(this.filterReview)
        companyRepo.showCompanies(showCompanyPayload, object:Callbacks.ShowCompanyCallback{
            override fun showCompanySuccess(response: List<Company>) {
                updateLiveData(response, _showCompanyResponse)
            }

            override fun showCompanyFailure(error: String?) {
                if (error != null) {
                    updateLiveDataWithError(error, _showCompanyResponse)
                }
            }
        })
    }

    fun showCompaniesInRealTime() {
        var showCompanyPayload = ShowCompanyPayload().setReview(this.filterReview)
        companyRepo.showCompaniesInRealTime(showCompanyPayload, object:Callbacks.ShowCompanyCallback{
            override fun showCompanySuccess(response: List<Company>) {
                updateLiveData(response, _showCompanyResponse)
            }

            override fun showCompanyFailure(error: String?) {
                if (error != null) {
                    updateLiveDataWithError(error, _showCompanyResponse)
                }
            }
        })
    }
}