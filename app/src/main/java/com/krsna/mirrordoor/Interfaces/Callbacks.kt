package com.krsna.mirrordoor.Interfaces

import com.example.base.models.BaseResponse
import com.krsna.mirrordoor.Model.Company

interface Callbacks {

    interface AddCompanyCallback {
        fun companyAddedSuccess(response: BaseResponse)
        fun companyAddedFailure(error: String?)
    }

    interface ShowCompanyCallback {
        fun showCompanySuccess(response: List<Company>)
        fun showCompanyFailure(error: String?)
    }

    interface OpenFragments {
        fun openAddCompanyFragment()
        fun openShowCompanyFragment()
    }
}